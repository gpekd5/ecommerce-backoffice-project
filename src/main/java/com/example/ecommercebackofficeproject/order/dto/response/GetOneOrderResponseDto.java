package com.example.ecommercebackofficeproject.order.dto.response;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneOrderResponseDto {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String customerEmail;
    private final String productName;
    private final Integer quantity;
    private final Integer totalPrice;
    private final OrderStatus orderStatus;
    private final String createdByAdminName;
    private final String createdByAdminEmail;
    private final String createdByAdminRole;
    private final LocalDateTime orderedAt;

    @Builder
    public GetOneOrderResponseDto(Long id, String orderNumber, String customerName, String customerEmail, String productName, Integer quantity, Integer totalPrice, OrderStatus orderStatus, String createdByAdminName, String createdByAdminEmail, String createdByAdminRole, LocalDateTime orderedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdByAdminName = createdByAdminName;
        this.createdByAdminEmail = createdByAdminEmail;
        this.createdByAdminRole = createdByAdminRole;
        this.orderedAt = orderedAt;
    }
}
