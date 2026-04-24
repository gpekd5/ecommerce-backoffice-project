package com.example.ecommercebackofficeproject.order.dto.response;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateOrderResponseDto {
    private final Long id;
    private final String orderNumber;
    private final Long customerId;
    private final Long productId;
    private final Integer quantity;
    private final Integer totalPrice;
    private final OrderStatus orderStatus;
    private final LocalDateTime orderedAt;
    private final Long createdByAdminId;


    @Builder
    public CreateOrderResponseDto(Long id, String orderNumber, Long customerId, Long productId, Integer quantity, Integer totalPrice, OrderStatus orderStatus, LocalDateTime orderedAt, Long createdByAdminId) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderedAt = orderedAt;
        this.createdByAdminId = createdByAdminId;
    }
}
