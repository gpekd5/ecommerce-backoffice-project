package com.example.ecommercebackofficeproject.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DashboardResponseDto {
    private final DashboardSummaryDto summary;
    private final DashboardWidgetsDto widgets;
    private final DashboardChartsDto charts;
    private final List<DashboardRecentOrderDto> recentOrders;

    @Builder
    public DashboardResponseDto(DashboardSummaryDto summary, DashboardWidgetsDto widgets, DashboardChartsDto charts, List<DashboardRecentOrderDto> recentOrders) {
        this.summary = summary;
        this.widgets = widgets;
        this.charts = charts;
        this.recentOrders = recentOrders;
    }
}
