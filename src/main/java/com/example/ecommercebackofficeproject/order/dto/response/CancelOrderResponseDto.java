package com.example.ecommercebackofficeproject.order.dto.response;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CancelOrderResponseDto {
    private final long id;
    private final String orderNumber;
    private final OrderStatus orderStatus;
    private final String cancelReason;
    private final LocalDateTime canceledAt;
    private final Long productId;
    private final String productName;
    private final Integer restoredQuantity;
    private final Integer currentStock;
    private final ProductStatus productStatus;

    @Builder
    public CancelOrderResponseDto(long id, String orderNumber, OrderStatus orderStatus, String cancelReason, LocalDateTime canceledAt, Long productId, String productName, Integer restoredQuantity, Integer currentStock, ProductStatus productStatus) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.cancelReason = cancelReason;
        this.canceledAt = canceledAt;
        this.productId = productId;
        this.productName = productName;
        this.restoredQuantity = restoredQuantity;
        this.currentStock = currentStock;
        this.productStatus = productStatus;
    }
}
