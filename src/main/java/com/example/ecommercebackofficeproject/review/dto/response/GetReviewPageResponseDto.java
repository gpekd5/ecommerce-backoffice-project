package com.example.ecommercebackofficeproject.review.dto.response;

import com.example.ecommercebackofficeproject.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 리뷰 전체 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * Product 엔티티를 클라이언트에 필요한 정보만 담은 응답 객체로 변환합니다.
 */
@Getter
public class GetReviewPageResponseDto {

    private Long reviewId;              // 리뷰 고유식별자
    private String orderNumber;         // 주문번호
    private String customerName;        // 고객명
    private String productName;         // 상품명
    private int rating;                 // 평점
    private String comment;             // 리뷰내용
    private LocalDateTime createdAt;    // 작성일

    /**
     * Review 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param review 변환할 상품 엔티티 객체
     */
    public GetReviewPageResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.orderNumber = review.getOrder().getOrderNumber();
        this.customerName = review.getCustomer().getName();
        this.productName = review.getProduct().getProductName();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }
}
