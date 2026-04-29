package com.example.ecommercebackofficeproject.dashboard.dto;

import com.example.ecommercebackofficeproject.product.type.ProductCategory;

public interface ProductCategoryCountDto {

    ProductCategory getCategory();

    Long getCount();
}