package com.example.ecommercebackofficeproject.dashboard.dto;

import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomerStatusChartDto {
    private final CustomerStatus status;
    private final Long count;

    @Builder
    public CustomerStatusChartDto(CustomerStatus status, Long count) {
        this.status = status;
        this.count = count;
    }
}
