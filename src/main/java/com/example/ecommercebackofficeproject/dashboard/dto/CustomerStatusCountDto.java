package com.example.ecommercebackofficeproject.dashboard.dto;

import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;

public interface CustomerStatusCountDto {

    CustomerStatus getStatus();

    Long getCount();
}