package com.example.ecommercebackofficeproject.order.dto.response;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetListOrderItemResponseDto {
    private final Long id;
    private final String customerName;
    private final String productName;
    private final Integer quantity;
    private final Integer totalPrice;
    private final LocalDateTime orderedAt;
    private final OrderStatus orderStatus;
    private final String createdByAdminName;

    @Builder
    public GetListOrderItemResponseDto(Long id, String customerName, String productName, Integer quantity, Integer totalPrice, LocalDateTime orderedAt, OrderStatus orderStatus, String createdByAdminName) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderedAt = orderedAt;
        this.orderStatus = orderStatus;
        this.createdByAdminName = createdByAdminName;
    }
}
