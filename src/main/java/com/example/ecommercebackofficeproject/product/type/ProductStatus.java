package com.example.ecommercebackofficeproject.product.type;

import com.example.ecommercebackofficeproject.global.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 상품의 판매 상태를 정의하는 Enum 클래스입니다.
 * 서비스 내에서 상품이 판매 가능한지, 혹은 품절/단종되었는지를 관리하며,
 * 각 상태는 클라이언트 응답을 위한 한글 설명(description)을 포함합니다.
 * 주요 특징:
 *     JSON 응답 시 한글 명칭(예: "판매중")으로 직렬화됩니다.
 *     JSON 요청 시 영문 상수명(예: "AVAILABLE")을 기준으로 대소문자 구분 없이 매핑 및 검증을 수행합니다.
 */
@Getter
public enum ProductStatus {
    AVAILABLE("판매중"),
    SOLD_OUT("품절"),
    DISCONTINUED("단종");

    @JsonValue
    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    @JsonCreator
    public static ProductStatus from(String value) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.name().equals(value.toUpperCase())) {
                return status;
            }
        }
        throw new BadRequestException("존재하지 않는 상품 상태입니다.");
    }
}
