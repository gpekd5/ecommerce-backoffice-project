package com.example.ecommercebackofficeproject.product.dto.response;

import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 상품 정보를 반환하기 위한 공통 응답 DTO 클래스입니다.
 */
@Getter
public class ProductResponseDto {

    private final Long productId;             // 상품 고유식별자
    private final String productName;         // 상품명
    private final ProductCategory category;   // 카테고리
    private final Long price;                 // 가격
    private final int stock;                  // 재고
    private final ProductStatus status;       // 상태
    private final LocalDateTime createdAt;    // 등록일
    private final String createdByName;       // 등록 관리자명

    /**
     * Product 엔티티를 응답 DTO로 변환합니다.
     * @param product 상품 엔티티
     */
    public ProductResponseDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();

        // 관리자 탈퇴 여부에 따른 이름 매핑 처리
        if (product.getAdmin().getDeletedAt() == null) {
            this.createdByName = product.getAdmin().getName();
        } else {
            this.createdByName = "알 수 없음";
        }
    }
}
