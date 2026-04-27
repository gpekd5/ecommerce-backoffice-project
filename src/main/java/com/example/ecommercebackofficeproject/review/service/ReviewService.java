package com.example.ecommercebackofficeproject.review.service;

import com.example.ecommercebackofficeproject.review.dto.response.GetReviewPageResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.GetReviewResponseDto;
import com.example.ecommercebackofficeproject.review.entity.Review;
import com.example.ecommercebackofficeproject.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * <p>
     * 검색 키워드(고객명, 상품명)와 평점 필터를 적용하며,
     * 조회된 엔티티 목록을 응답 DTO(GetReviewPageResponseDto)로 변환하여 반환합니다.
     *
     * @param keyword  검색 키워드 (고객명 또는 상품명)
     * @param rating   조회할 평점 (1~5)
     * @param pageable 페이징 및 정렬 정보
     * @return 페이징 처리된 리뷰 응답 DTO 목록
     */
    @Transactional(readOnly = true)
    public Page<GetReviewPageResponseDto> getReviewList(String keyword, int rating, Pageable pageable) {
        return reviewRepository.findAllWithFilters(keyword, rating, pageable).map(GetReviewPageResponseDto::new);
    }

    /**
     * 특정 리뷰의 상세 정보를 조회합니다.
     * @param reviewId 조회할 리뷰의 고유 식별자(ID)
     * @return 조회된 리뷰 상세 정보 DTO
     * @throws IllegalArgumentException 존재하지 않는 리뷰 ID를 조회하려 할 경우 발생
     */
    public GetReviewResponseDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException(""));

        if(review.isDeleted()) {
            throw new IllegalStateException("삭제된 상품입니다.");
        }

        return new GetReviewResponseDto(review);
    }
}
