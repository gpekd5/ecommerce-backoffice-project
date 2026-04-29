package com.example.ecommercebackofficeproject.review.service;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.global.exception.NotFoundException;
import com.example.ecommercebackofficeproject.global.exception.UnauthorizedException;
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
 * 리뷰 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 필터 조건(키워드, 평점)에 따른 리뷰 목록을 페이징 조회합니다.
     *
     * @param keyword  검색 키워드 (고객명 또는 상품명)
     * @param rating   조회할 평점 (1~5)
     * @param pageable 페이징 및 정렬 정보
     * @return 리뷰 목록 페이징 응답 DTO
     */
    @Transactional(readOnly = true)
    public GetReviewPageResponseDto getReviewList(String keyword, Integer rating, Pageable pageable) {

        Page<Review> reviewPage = reviewRepository.findAllWithFilters(keyword, rating, pageable);

        return new GetReviewPageResponseDto(reviewPage);
    }

    /**
     * 특정 리뷰의 상세 정보를 조회합니다.
     *
     * @param reviewId 리뷰 고유 식별자
     * @return 리뷰 상세 정보 DTO
     * @throws NotFoundException 존재하지 않거나 이미 삭제된 리뷰일 경우 발생
     */
    @Transactional(readOnly = true)
    public GetReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("review with ID " + reviewId + "not found."));

        if(review.isDeleted()) {
            throw new NotFoundException("삭제된 상품입니다.");
        }

        return new GetReviewResponseDto(review);
    }

    /**
     * 리뷰를 논리적으로 삭제(Soft Delete) 처리합니다.
     *
     * @param sessionAdmin 삭제를 진행하려는 관리자 로그인정보
     * @param reviewId     삭제할 리뷰 고유 식별자
     * @throws UnauthorizedException 삭제하려는 관리자에게 권한이 없는 경우 발생
     * @throws NotFoundException 존재하지 않는 리뷰 식별자일 경우 발생
     */
    @Transactional
    public void deleteReview(SessionAdminDto sessionAdmin, Long reviewId) {

        if(sessionAdmin.getAdminRole().equals(AdminRole.CS.name())) {
            throw new UnauthorizedException("해당 권한을 가지고 있지 않습니다.");
        }

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("review with ID " + reviewId + "not found."));

        review.delete();
    }

    /**
     * 상품별 리뷰 통계(평균 평점, 총 개수, 별점 분포)를 조회합니다.
     * 별점 분포는 1점부터 5점까지 기본값(0)으로 초기화 후 DB 데이터를 매핑합니다.
     *
     * @param productId 상품 고유 식별자
     * @return 리뷰 통계 응답 DTO
     */
    @Transactional(readOnly = true)
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
     * 상품의 최신 리뷰 3건을 조회합니다.
     *
     * @param productId 상품 고유 식별자
     * @return 최신 리뷰 응답 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<RecentReviewResponseDto> getRecentReviews(Long productId) {
        return reviewRepository.findRecentReviewTop3(productId, PageRequest.of(0, 3)).stream().map(RecentReviewResponseDto::new).toList();
    }

}