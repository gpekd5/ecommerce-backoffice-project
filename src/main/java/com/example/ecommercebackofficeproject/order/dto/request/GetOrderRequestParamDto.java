package com.example.ecommercebackofficeproject.order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetOrderRequestParamDto {
    private String keyword;
    private Integer page = 1;
    private Integer size = 10;
    private String sortBy = "orderedAt";
    private String sortDirection = "DESC";
    private String orderStatus;
}
