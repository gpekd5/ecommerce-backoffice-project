package com.example.ecommercebackofficeproject.customer.dto.response;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCustomerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private Long totalOrderCount;
    private Long totalOrderAmount;

    public GetCustomerResponseDto(
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

    public static GetCustomerResponseDto from(
            Customer customer,
            Long totalOrderCount,
            Long totalOrderAmount
    ) {
        return new GetCustomerResponseDto(
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
