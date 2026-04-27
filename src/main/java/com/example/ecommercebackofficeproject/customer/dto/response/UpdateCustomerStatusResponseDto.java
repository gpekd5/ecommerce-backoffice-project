package com.example.ecommercebackofficeproject.customer.dto.response;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Getter;

@Getter
public class UpdateCustomerStatusResponseDto {

    private final Long id;
    private final CustomerStatus status;

    public UpdateCustomerStatusResponseDto(Long id, CustomerStatus status) {
        this.id = id;
        this.status = status;
    }

    public static UpdateCustomerStatusResponseDto from(Customer customer) {
        return new UpdateCustomerStatusResponseDto(
                customer.getId(),
                customer.getStatus()
        );
    }

}
