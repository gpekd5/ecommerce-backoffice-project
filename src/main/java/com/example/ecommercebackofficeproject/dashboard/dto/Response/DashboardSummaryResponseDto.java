package com.example.ecommercebackofficeproject.dashboard.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DashboardSummaryResponseDto {
    private final Long totalAdminCount;
    private final Long activeAdminCount;

    private final Long totalCustomerCount;
    private final Long activeCustomerCount;

    private final Long totalProductCount;
    private final Long lowStockProductCount;

    private final Long totalOrderCount;
    private final Long todayOrderCount;

    private final Long totalReviewCount;
    private final Double averageRating;

    @Builder
    public DashboardSummaryResponseDto(Long totalAdminCount, Long activeAdminCount, Long totalCustomerCount, Long activeCustomerCount, Long totalProductCount, Long lowStockProductCount, Long totalOrderCount, Long todayOrderCount, Long totalReviewCount, Double averageRating) {
        this.totalAdminCount = totalAdminCount;
        this.activeAdminCount = activeAdminCount;
        this.totalCustomerCount = totalCustomerCount;
        this.activeCustomerCount = activeCustomerCount;
        this.totalProductCount = totalProductCount;
        this.lowStockProductCount = lowStockProductCount;
        this.totalOrderCount = totalOrderCount;
        this.todayOrderCount = todayOrderCount;
        this.totalReviewCount = totalReviewCount;
        this.averageRating = averageRating;
    }
}
