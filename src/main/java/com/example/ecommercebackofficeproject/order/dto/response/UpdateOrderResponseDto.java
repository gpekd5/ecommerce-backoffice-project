package com.example.ecommercebackofficeproject.order.dto.response;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;

public class UpdateOrderResponseDto {
    private final Long id;
    private final OrderStatus orderStatus;
    private final LocalDateTime updatedAt;

    @Builder
    public UpdateOrderResponseDto(Long id, OrderStatus orderStatus, LocalDateTime updatedAt) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.updatedAt = updatedAt;
    }
}
