package com.example.ecommercebackofficeproject.review.dto.response;

import com.example.ecommercebackofficeproject.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 리뷰 상세 조회 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * Review 엔티티를 클라이언트에 필요한 정보만 담은 응답 객체로 변환합니다.
 */
@Getter
public class GetReviewResponseDto {
    private String productName;         // 상품명
    private String customerName;        // 고객명
    private String customerEmail;       // 고객 이메일
    private LocalDateTime createdAt;    // 작성일
    private int rating;                 // 평점
    private String comment;             // 리뷰내용

    /**
     * Review 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param review 변환할 리뷰 엔티티 객체
     */
    public GetReviewResponseDto(Review review) {
        this.productName = review.getProduct().getProductName();
        this.customerName = review.getCustomer().getName();
        this.customerEmail = review.getCustomer().getEmail();
        this.createdAt = review.getCreatedAt();
        this.rating = review.getRating();
        this.comment = review.getComment();
    }
}
