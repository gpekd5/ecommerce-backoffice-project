package com.example.ecommercebackofficeproject.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DashboardChartsDto {
    // 리뷰 평점 분포, 고객 상태 분포, 상품 카테고리 분포
    private final List<ReviewRatingChartDto> reviewRating;
    private final List<CustomerStatusChartDto> customerStatus;
    private final List<ProductCategoryChartDto> productCategory;

    @Builder
    public DashboardChartsDto(List<ReviewRatingChartDto> reviewRating, List<CustomerStatusChartDto> customerStatus, List<ProductCategoryChartDto> productCategory) {
        this.reviewRating = reviewRating;
        this.customerStatus = customerStatus;
        this.productCategory = productCategory;
    }
}
