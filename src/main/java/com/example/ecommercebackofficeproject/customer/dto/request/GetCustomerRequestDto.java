package com.example.ecommercebackofficeproject.customer.dto.request;

import lombok.Getter;

@Getter
public class GetCustomerRequestDto {
    private String keyword;
    private String status;
    private int page = 1;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDir = "desc";

}
