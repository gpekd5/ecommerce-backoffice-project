package com.example.ecommercebackofficeproject.product.dto.response;

import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import com.example.ecommercebackofficeproject.review.dto.response.RecentReviewResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.ReviewStatsResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 상품 상세 정보 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 */
@Getter
public class GetProductResponseDto {

    private final String productName;         // 상품명
    private final ProductCategory category;   // 카테고리
    private final Long price;                 // 가격
    private final int stock;                  // 재고
    private final ProductStatus status;       // 상태
    private final LocalDateTime createdAt;    // 등록일
    private final String createdByName;       // 등록 관리자명
    private final String createdByEmail;      // 등록 관리자 이메일

    private final ReviewStatsResponseDto reviewStats;             // 리뷰 통계
    private final List<RecentReviewResponseDto> recentReviews;    // 최신 리뷰 목록

    /**
     * 상품 엔티티와 리뷰 관련 데이터를 결합하여 응답 DTO를 생성합니다.
     * @param product       상품 엔티티
     * @param reviewStats   리뷰 통계 데이터
     * @param recentReviews 최신 리뷰 목록
     */
    public GetProductResponseDto(Product product, ReviewStatsResponseDto reviewStats, List<RecentReviewResponseDto> recentReviews) {
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();

        // 관리자 탈퇴 여부에 따른 이름 매핑 처리
        if (product.getAdmin().getDeletedAt() == null) {
            this.createdByName = product.getAdmin().getName();
            this.createdByEmail = product.getAdmin().getEmail();
        } else {
            this.createdByName = "알 수 없음";
            this.createdByEmail = null;
        }

        this.reviewStats = reviewStats;
        this.recentReviews = recentReviews;
    }
}
