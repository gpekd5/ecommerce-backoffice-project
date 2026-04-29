package com.example.ecommercebackofficeproject.review.service;

import com.example.ecommercebackofficeproject.review.dto.response.GetReviewPageResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.GetReviewResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.RecentReviewResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.ReviewStatsResponseDto;
import com.example.ecommercebackofficeproject.review.entity.Review;
import com.example.ecommercebackofficeproject.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 리뷰 관리 시스템의 비즈니스 로직을 구현하는 서비스 클래스입니다.
 * 리뷰에 대한 전체조회, 상세조회, 삭제 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 검색 조건에 따른 리뷰 목록을 페이징하여 조회합니다.
     * 검색 키워드(고객명, 상품명)와 평점 필터를 적용하며,
     * 조회된 엔티티 목록을 응답 DTO(GetReviewPageResponseDto)로 변환하여 반환합니다.
     *
     * @param keyword  검색 키워드 (고객명 또는 상품명)
     * @param rating   조회할 평점 (1~5)
     * @param pageable 페이징 및 정렬 정보
     * @return 페이징 처리된 리뷰 응답 DTO 목록
     */
    @Transactional(readOnly = true)
    public GetReviewPageResponseDto getReviewList(String keyword, Integer rating, Pageable pageable) {

        Page<Review> reviewPage = reviewRepository.findAllWithFilters(keyword, rating, pageable);

        return new GetReviewPageResponseDto(reviewPage);
    }

    /**
     * 특정 리뷰의 상세 정보를 조회합니다.
     *
     * @param reviewId 조회할 리뷰의 고유 식별자(ID)
     * @return 조회된 리뷰 상세 정보 DTO
     * @throws IllegalArgumentException 존재하지 않는 리뷰 ID를 조회하려 할 경우 발생
     */
    @Transactional
    public GetReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        if(review.isDeleted()) {
            throw new IllegalStateException("삭제된 상품입니다.");
        }

        return new GetReviewResponseDto(review);
    }

    /**
     * 특정 리뷰를 논리적으로 삭제합니다.
     * 리뷰 엔티티의 삭제 상태를 업데이트하여 실제 데이터는 보존하되,
     * 향후 조회 목록에서는 제외되도록 처리합니다.
     *
     * @param reviewId 삭제할 리뷰의 고유 식별자(ID)
     * @throws IllegalArgumentException 해당 ID를 가진 리뷰가 DB에 존재하지 않을 경우 발생
     */
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        review.delete();
    }

    /**
     * 특정 상품의 리뷰 통계 정보를 조회합니다.
     * DB에서 직접 계산된 평균 평점과 전체 리뷰 개수를 가져오며,
     * 별점별 분포(1점~5점)를 Map 형태로 가공하여 반환합니다.
     *
     * @param productId 리뷰 통계를 조회할 상품의 고유 식별자
     * @return 평균 평점, 총 리뷰 수, 별점 분포가 담긴 통계 DTO
     */
    public ReviewStatsResponseDto getReviewStat(Long productId) {

        Double avg = reviewRepository.getAvgRating(productId);
        double averageRating = (avg != null) ? avg : 0.0;

        long totalCount = reviewRepository.getReviewCnt(productId);


        Map<Integer, Long> ratingDistribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            ratingDistribution.put(i, 0L);
        }

        List<Object[]> reviews = reviewRepository.getReviewDistribution(productId);

        for(Object[] row: reviews) {
            ratingDistribution.put((Integer) row[0], (Long) row[1]);
        }

        return new ReviewStatsResponseDto(averageRating, totalCount, ratingDistribution);
    }

    /**
     * 특정 상품의 최신 리뷰 목록 3건을 조회합니다.
     * 작성일(createdAt) 기준 내림차순으로 정렬된 리뷰 엔티티를
     * 클라이언트 응답용 DTO 목록으로 변환하여 반환합니다.
     *
     * @param productId 최신 리뷰를 조회할 상품의 고유 식별자
     * @return 최신 리뷰 3건의 정보가 담긴 DTO 리스트
     */
    public List<RecentReviewResponseDto> getRecentReviews(Long productId) {
        return reviewRepository.findRecentReviewTop3(productId, PageRequest.of(0, 3)).stream().map(RecentReviewResponseDto::new).toList();
    }
}
