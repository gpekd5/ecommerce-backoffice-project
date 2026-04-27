package com.example.ecommercebackofficeproject.order.dto;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;

public class GetOrderRequestParamDto {
    String keyword;
    Integer page;
    Integer size;
    String sortBy;
    String sortDirection;
    OrderStatus orderStatus;
}
