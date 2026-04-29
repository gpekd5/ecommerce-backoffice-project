package com.example.ecommercebackofficeproject.review.dto.response;

import com.example.ecommercebackofficeproject.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 리뷰 정보를 반환하기 위한 응답 DTO 클래스입니다.
 */
@Getter
public class ReviewResponseDto {

    private Long reviewId;              // 리뷰 고유식별자
    private String orderNumber;         // 주문번호
    private String customerName;        // 고객명
    private String productName;         // 상품명
    private int rating;                 // 평점
    private String comment;             // 리뷰내용
    private LocalDateTime createdAt;    // 작성일

    /**
     * Review 엔티티를 응답 DTO로 변환합니다.
     *
     * @param review 리뷰 엔티티
     */
    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.orderNumber = review.getOrder().getOrderNumber();
        this.customerName = review.getCustomer().getName();
        this.productName = review.getProduct().getProductName();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }
}
