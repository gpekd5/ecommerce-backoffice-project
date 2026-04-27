package com.example.ecommercebackofficeproject.customer.dto.response;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Getter;

@Getter
public class UpdateCustomerResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;

    public UpdateCustomerResponseDto(
            Long id,
            String name,
            String email,
            String phone,
            CustomerStatus status
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public static UpdateCustomerResponseDto from(Customer customer) {
        return new UpdateCustomerResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus()
        );
    }

}
