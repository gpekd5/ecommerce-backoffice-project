package com.example.ecommercebackofficeproject.product.dto.request;

import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductUpdateStatusDto {

    /**
     * 상태
     * 필수 입력값입니다.
     */
    @NotNull(message = "상태는 필수 입력값입니다.")
    private ProductStatus status;
}
