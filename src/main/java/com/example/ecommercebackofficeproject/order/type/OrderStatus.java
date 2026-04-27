package com.example.ecommercebackofficeproject.order.type;

public enum OrderStatus {
    PREPARING,
    SHIPPING,
    DELIVERED,
    CANCELED;

    public boolean canChangeTo(OrderStatus nextStatus) {
        if (this == PREPARING) {
            return nextStatus == SHIPPING || nextStatus == CANCELED;
        } else if (this == SHIPPING) {
            return nextStatus == DELIVERED;
        } else {
            return false;
        }
    }
}
