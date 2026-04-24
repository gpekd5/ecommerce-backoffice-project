package com.example.ecommercebackofficeproject.product.dto.request;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 상품 등록 및 수정 요청 시 데이터를 전달받는 DTO 클래스입니다.
 */
@Getter
public class ProductRequestDto {

    /**
     * 상품명
     * 필수 입력값입니다.
     */
    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String productName;

    /**
     * 카테고리
     * 필수 입력값입니다.
     */
    @NotNull(message = "카테고리는 필수 입력값입니다.")
    private ProductCategory category;

    /**
     * 가격
     * 필수 입력값입니다.
     * 최소 0이상의 자연수를 입력해주어야 합니다.
     */
    @NotNull(message = "가격은 필수 입력값입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Long price;

    /**
     * 재고
     * 최소 0이상의 자연수를 입력해주어야 합니다.
     */
    @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
    private int stock;

    /**
     * 상태
     * 필수 입력값입니다.
     */
    @NotNull(message = "상태는 필수 입력값입니다.")
    private ProductStatus status;

    /**
     * DTO 데이터를 기반으로 Product 엔티티 객체를 생성합니다.
     * @param admin 상품을 등록하는 작성자(Admin) 엔티티
     * @return 빌더 패턴이 적용된 Product 엔티티 객체
     */
    public Product toEntity(Admin admin) {
        return Product.builder()
                .productName(this.productName)
                .category(this.category)
                .price(this.price)
                .stock(this.stock)
                .status(this.status)
                .admin(admin)
                .build();
    }
}
