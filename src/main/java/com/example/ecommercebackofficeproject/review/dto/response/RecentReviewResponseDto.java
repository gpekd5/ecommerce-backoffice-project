package com.example.ecommercebackofficeproject.review.dto.response;

import com.example.ecommercebackofficeproject.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 상품 상세 페이지에 표시될 최신 리뷰 정보를 전달하는 DTO입니다.
 * Review 엔티티를 바탕으로 클라이언트에게 필요한
 * 고객 이름, 평점, 내용, 작성일 정보를 추출하여 제공합니다.
 */
@Getter
public class RecentReviewResponseDto {

    private String customerName;        // 고객명
    private int rating;                 // 평점
    private String comment;             // 리뷰내용
    private LocalDateTime createdAt;    // 작성일

    /**
     * Review 엔티티로부터 응답용 DTO를 생성합니다.
     * @param review 변환할 리뷰 엔티티 객체
     */
    public RecentReviewResponseDto(Review review) {
        this.customerName = review.getCustomer().getName();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }
}
