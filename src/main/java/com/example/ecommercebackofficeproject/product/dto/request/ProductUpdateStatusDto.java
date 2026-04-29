package com.example.ecommercebackofficeproject.product.dto.request;

import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 상품 상태 수정 요청 데이터를 전달받는 DTO 클래스입니다.
 */
@Getter
public class ProductUpdateStatusDto {

    /**
     * 상태
     * 필수 입력값입니다.
     */
    @NotNull(message = "상태는 필수 입력값입니다.")
    private ProductStatus status;
}
