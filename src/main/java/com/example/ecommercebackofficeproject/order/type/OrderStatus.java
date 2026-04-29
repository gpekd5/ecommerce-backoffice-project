package com.example.ecommercebackofficeproject.order.type;

import com.example.ecommercebackofficeproject.global.exception.BadRequestException;

public enum OrderStatus {
    PREPARING,
    SHIPPING,
    DELIVERED,
    CANCELED;

    public void validateChangeTo(OrderStatus nextStatus) {
        if (this == nextStatus) {
            throw new BadRequestException("이미 해당 상태로 설정되어 있습니다.");
        }

        boolean changeable = switch (this) {
            case PREPARING -> nextStatus == SHIPPING || nextStatus == CANCELED;
            case SHIPPING -> nextStatus == DELIVERED;
            case DELIVERED, CANCELED -> false;
        };

        if (!changeable) {
            throw new BadRequestException("현재 상태에서는 해당 상태로 변경할 수 없습니다.");
        }
    }
}
