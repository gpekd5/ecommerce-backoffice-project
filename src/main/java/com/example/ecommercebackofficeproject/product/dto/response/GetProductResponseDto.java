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
 * 상품 정보 상세 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * 상품의 기본 정보와 관리자 정보, 그리고 리뷰 통계 및 최신 리뷰 목록을 포함합니다.
 */
@Getter
public class GetProductResponseDto {

    private String productName;         // 상품명
    private ProductCategory category;   // 카테고리
    private Long price;                 // 가격
    private int stock;                  // 재고
    private ProductStatus status;       // 상태
    private LocalDateTime createdAt;    // 등록일
    private String createdByName;       // 등록 관리자명
    private String createdByEmail;      // 등록 관리자 이메일

    private ReviewStatsResponseDto reviewStats;             // 리뷰 통계
    private List<RecentReviewResponseDto> recentReviews;    // 최신 리뷰 목록

    /**
     * Product 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param product 변환할 상품 엔티티 객체
     */
    public GetProductResponseDto(Product product) {
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();

        // 연관관계가 있는 관리자 정보 (관리자 삭제시 해당 관리자가 만든 상품을 어떻게 처리 할지 안정해서 일단 이렇게 작성)
        if (product.getAdmin().getDeletedAt() != null) {
            this.createdByName = product.getAdmin().getName();
            this.createdByEmail = product.getAdmin().getEmail();
        } else {
            this.createdByName = "알 수 없음";
            this.createdByEmail = null;
        }
    }

    /**
     * 기본 상품 정보와 리뷰 데이터를 함께 포함하여 DTO를 생성합니다.
     * @param product 변환할 상품 엔티티 객체
     * @param reviewStats 해당 상품의 리뷰 통계(평균평점, 총 리뷰수, 별점별 개수)
     * @param recentReviews 해당 상품의 최신 리뷰 3개(작성일 기준 내림차순)
     */
    public GetProductResponseDto(Product product, ReviewStatsResponseDto reviewStats, List<RecentReviewResponseDto> recentReviews) {
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();

        // 연관관계가 있는 관리자 정보 (관리자 삭제시 해당 관리자가 만든 상품을 어떻게 처리 할지 안정해서 일단 이렇게 작성)
        if (product.getAdmin().getDeletedAt() != null) {
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
