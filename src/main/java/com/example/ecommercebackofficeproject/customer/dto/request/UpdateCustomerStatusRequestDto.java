package com.example.ecommercebackofficeproject.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCustomerStatusRequestDto {

    @NotBlank(message = "상태는 필수입니다")
    private String status;
}
