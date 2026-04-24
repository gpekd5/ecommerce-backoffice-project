package com.example.ecommercebackofficeproject.customer.dto.response;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCustomerDetailResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    private final LocalDateTime createdAt;
    private final Long totalOrderCount;
    private final Long totalOrderAmount;

    public GetCustomerDetailResponseDto(
            Long id,
            String name,
            String email,
            String phone,
            CustomerStatus status,
            LocalDateTime createdAt,
            Long totalOrderCount,
            Long totalOrderAmount
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.totalOrderCount = totalOrderCount;
        this.totalOrderAmount = totalOrderAmount;
    }

    public static GetCustomerDetailResponseDto from(
            Customer customer,
            Long totalOrderCount,
            Long totalOrderAmount
    ) {
        return new GetCustomerDetailResponseDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getCreatedAt(),
                totalOrderCount,
                totalOrderAmount
        );
    }

}
