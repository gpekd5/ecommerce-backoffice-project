package com.example.ecommercebackofficeproject.product.dto.response;

import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 상품 등록 성공 시 반환되는 응답 DTO 클래스입니다.
 */
@Getter
public class CreateProductResponseDto {

    private final Long productId;             // 상품 고유식별자
    private final String productName;         // 상품명
    private final ProductCategory category;   // 카테고리
    private final Long price;                 // 가격
    private final int stock;                  // 재고
    private final ProductStatus status;       // 상태
    private final LocalDateTime createdAt;    // 등록일
    private final Long createdBy;             // 등록 관리자 고유식별자
    private final String createdByName;       // 등록 관리자명
    private final String createdByEmail;      // 등록 관리자 이메일

    /**
     * Product 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     * @param product 변환할 상품 엔티티 객체
     */
    public CreateProductResponseDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();
        this.createdBy = product.getAdmin().getId();
        this.createdByName = product.getAdmin().getName();
        this.createdByEmail = product.getAdmin().getEmail();
    }

}
