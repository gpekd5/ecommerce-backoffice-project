package com.example.ecommercebackofficeproject.order.type;

import com.example.ecommercebackofficeproject.global.exception.BadRequestException;

public enum OrderSortBy {
    CREATED_AT,
    TOTAL_PRICE,
    ORDER_STATUS;

    public static OrderSortBy from(String value) {
        try {
            return OrderSortBy.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("지원하지 않는 정렬 기준입니다.");
        }
    }
}
