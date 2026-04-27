package com.example.ecommercebackofficeproject.order.dto.request;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import lombok.Getter;

@Getter
public class GetOrderRequestParamDto {
    String keyword;
    Integer page = 1;
    Integer size = 10;
    String sortBy = "orderedAt";
    String sortDirection = "DESC";
    OrderStatus orderStatus;
}
