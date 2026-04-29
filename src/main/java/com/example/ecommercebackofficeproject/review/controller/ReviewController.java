package com.example.ecommercebackofficeproject.review.controller;

import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.global.response.ApiResponse;
import com.example.ecommercebackofficeproject.review.dto.response.GetReviewPageResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.GetReviewResponseDto;
import com.example.ecommercebackofficeproject.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 리뷰 관련 요청을 처리하는 API 컨트롤러 클래스입니다.
 */
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 목록을 조건에 따라 페이징 조회합니다.
     * 키워드 검색(고객명, 상품명)과 평점 필터링을 지원합니다.
     *
     * @param keyword  검색 키워드 (선택)
     * @param rating   조회할 평점 (선택)
     * @param pageable 페이징 및 정렬 정보
     * @return 리뷰 목록 페이징 응답 DTO
     */
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<GetReviewPageResponseDto>> getReviewList(@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer rating, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "리뷰 전체 목록을 조회했습니다.", reviewService.getReviewList(keyword, rating, pageable)));
    }

    /**
     * 특정 리뷰의 상세 정보를 조회합니다.
     *
     * @param reviewId 리뷰 고유 식별자
     * @return 리뷰 상세 정보 DTO
     */
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<GetReviewResponseDto>> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "특정 리뷰를 조회했습니다.", reviewService.getReview(reviewId)));
    }

    /**
     * 리뷰를 논리적으로 삭제(Soft Delete)합니다.
     *
     * @param reviewId 삭제할 리뷰 식별자
     * @param sessionAdmin 현재 로그인한 관리자 정보
     * @return 성공 메시지
     */
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId, @RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        reviewService.deleteReview(sessionAdmin, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "리뷰가 삭제되었습니다.", null));
    }

}