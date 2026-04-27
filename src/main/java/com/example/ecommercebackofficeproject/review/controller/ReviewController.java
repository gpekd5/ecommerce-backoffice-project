package com.example.ecommercebackofficeproject.review.controller;

import com.example.ecommercebackofficeproject.review.dto.response.GetReviewPageResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.GetReviewResponseDto;
import com.example.ecommercebackofficeproject.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 리뷰 관리를 위한 API 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 목록을 페이징 처리하여 조회합니다.
     * 키워드(고객명, 상품명) 검색과 평점 필터링을 지원하며,
     * 페이징 및 정렬 조건(평점순, 작성일순 등)을 적용하여 결과를 반환합니다.
     *
     * @param keyword  검색할 키워드 (고객명 또는 상품명, 선택 사항)
     * @param rating   조회할 평점 필터 (1~5, 선택 사항)
     * @param pageable 페이징 및 정렬 정보 (page, size, sort)
     * @return 페이징 정보가 포함된 리뷰 응답 DTO 목록
     */
    @GetMapping("/reviews")
    public ResponseEntity<Page<GetReviewPageResponseDto>> getReviewList(@RequestParam(required = false) String keyword, @RequestParam(required = false) int rating, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviewList(keyword, rating, pageable));
    }

    /**
     * 특정 리뷰의 상세 정보를 조회합니다.
     * @param reviewId 조회할 리뷰의 고유 식별자(ID)
     * @return 조회된 리뷰 상세 정보 DTO를 담은 ResponseEntity
     */
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<GetReviewResponseDto> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }
}
