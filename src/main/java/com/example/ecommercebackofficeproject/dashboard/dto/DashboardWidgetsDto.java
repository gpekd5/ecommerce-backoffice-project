package com.example.ecommercebackofficeproject.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DashboardWidgetsDto {
    //총 매출 / 오늘 매출 / 준비중 주문 수 / 배송중 주문 수 / 배송완료 주문 수 / 재고 부족 상품 수 (재고 5개 이하) / 재고없음(품절) 상품 수
    private final Long totalRevenue;
    private final Long dailyRevenue;

    private final Long preparingOrderCount;
    private final Long shippingOrderCount;
    private final Long deliveredOrderCount;

    private final Long lowStockProductCount;
    private final Long outOfStockProductCount;

    @Builder
    public DashboardWidgetsDto(Long totalRevenue, Long dailyRevenue, Long preparingOrderCount, Long shippingOrderCount, Long deliveredOrderCount, Long lowStockProductCount, Long outOfStockProductCount) {
        this.totalRevenue = totalRevenue;
        this.dailyRevenue = dailyRevenue;
        this.preparingOrderCount = preparingOrderCount;
        this.shippingOrderCount = shippingOrderCount;
        this.deliveredOrderCount = deliveredOrderCount;
        this.lowStockProductCount = lowStockProductCount;
        this.outOfStockProductCount = outOfStockProductCount;
    }
}
