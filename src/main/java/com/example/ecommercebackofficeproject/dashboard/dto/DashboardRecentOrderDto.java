package com.example.ecommercebackofficeproject.dashboard.dto;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DashboardRecentOrderDto {
    // 최근 주문 10개 조회,(주문번호,고객명,상품명,금액,상태)
    private final Long id;
    private final String orderNumber;

    private final Long customerId;
    private final String customerName;
    private final String customerEmail;

    private final Long productId;
    private final String productName;

    private final Integer quantity;
    private final Long amount;

    private final LocalDateTime orderedAt;
    private final OrderStatus status;

    private final Long createdByAdminId;
    private final String createdByAdminName;
    private final String createdByAdminEmail;
    private final AdminRole createdByAdminRole;

    @Builder
    public DashboardRecentOrderDto(Long id, String orderNumber, Long customerId, String customerName, String customerEmail, Long productId, String productName, Integer quantity, Long amount, LocalDateTime orderedAt, OrderStatus status, Long createdByAdminId, String createdByAdminName, String createdByAdminEmail, AdminRole createdByAdminRole) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.orderedAt = orderedAt;
        this.status = status;
        this.createdByAdminId = createdByAdminId;
        this.createdByAdminName = createdByAdminName;
        this.createdByAdminEmail = createdByAdminEmail;
        this.createdByAdminRole = createdByAdminRole;
    }
}
