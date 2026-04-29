package com.example.ecommercebackofficeproject.product.dto.response;

import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductResponseDto {

    private Long productId;             // 상품 고유식별자
    private String productName;         // 상품명
    private ProductCategory category;   // 카테고리
    private Long price;                 // 가격
    private int stock;                  // 재고
    private ProductStatus status;       // 상태
    private LocalDateTime createdAt;    // 등록일
    private String createdByName;       // 등록 관리자명

    /**
     * Product 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param product 변환할 상품 엔티티 객체
     */
    public ProductResponseDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();

        // 연관관계가 있는 관리자 정보 (관리자 삭제시 해당 관리자가 만든 상품을 어떻게 처리 할지 안정해서 일단 이렇게 작성)
        if (product.getAdmin().getDeletedAt() == null) {
            this.createdByName = product.getAdmin().getName();
        } else {
            this.createdByName = "알 수 없음";
        }
    }
}
