package com.example.ecommercebackofficeproject.product.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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

}
