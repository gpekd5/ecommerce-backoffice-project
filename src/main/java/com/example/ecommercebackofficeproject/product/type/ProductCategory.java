package com.example.ecommercebackofficeproject.product.type;

import com.example.ecommercebackofficeproject.global.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 상품의 분류를 정의하는 카테고리 Enum 클래스입니다.
 * 전자기기, 식품, 패션 등 시스템에서 지원하는 카테고리 목록을 관리하며,
 *  * 각 카테고리는 클라이언트 응답을 위한 한글 설명(description)을 포함합니다.
 * 주요 기능:
 *   JSON 응답 시 한글 명칭(예: "전자기기")으로 직렬화됩니다.
 *   JSON 요청 시 영문 상수명(예: "ELECTRONICS")을 기준으로 대소문자 구분 없이 매핑 및 검증을 수행합니다.
 */
@Getter
public enum ProductCategory {
    ELECTRONICS("전자기기"),
    FASHION("패션/의류"),
    FOOD("식품"),
    LIVING("생활용품"),
    SPORTS("스포츠/레저"),
    BEAUTY("뷰티/화장품"),
    BOOKS("도서"),
    TOYS("완구/취미");

    @JsonValue
    private final String description;

    ProductCategory(String description) {
        this.description = description;
    }

    @JsonCreator
    public static ProductCategory from(String value) {
        for (ProductCategory category : ProductCategory.values()) {
            if (category.name().equals(value.toUpperCase())) {
                return category;
            }
        }
        throw new BadRequestException("존재하지 않는 상품 카테고리입니다.");
    }

}
