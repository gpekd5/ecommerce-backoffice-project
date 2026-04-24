package com.example.ecommercebackofficeproject.product.dto.response;

import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 상품 정보 등록 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * Product 엔티티를 클라이언트에 필요한 정보만 담은 응답 객체로 변환합니다.
 */
@Getter
public class CreateProductResponseDto {

    private Long productId;             // 상품 고유식별자
    private String productName;         // 상품명
    private ProductCategory category;   // 카테고리
    private Long price;                 // 가격
    private int stock;                  // 재고
    private ProductStatus status;       // 상태
    private LocalDateTime createdAt;    // 등록일
    private Long createdBy;             // 등록 관리자 고유식별자
    private String createdByName;       // 등록 관리자명
    private String createdByEmail;      // 등록 관리자 이메일

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
