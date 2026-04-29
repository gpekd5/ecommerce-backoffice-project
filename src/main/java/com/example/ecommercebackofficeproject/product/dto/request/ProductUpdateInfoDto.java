package com.example.ecommercebackofficeproject.product.dto.request;

import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductUpdateInfoDto {

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
}
