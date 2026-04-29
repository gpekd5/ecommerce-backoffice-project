package com.example.ecommercebackofficeproject.dashboard.dto;

import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryChartDto {
    private final ProductCategory category;
    private final Long count;

    @Builder
    public ProductCategoryChartDto(ProductCategory category, Long count) {
        this.category = category;
        this.count = count;
    }
}
