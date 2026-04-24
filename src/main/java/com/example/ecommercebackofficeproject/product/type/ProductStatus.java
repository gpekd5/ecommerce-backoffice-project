package com.example.ecommercebackofficeproject.product.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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

}
