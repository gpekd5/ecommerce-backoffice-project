package com.example.ecommercebackofficeproject.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 고객 상태 변경 요청 데이터를 담는 DTO.
 *
 * 고객 ID에 해당하는 고객의 상태를 변경할 때 사용한다.
 */
@Getter
public class UpdateCustomerStatusRequestDto {

    /**
     * 변경할 고객 상태.
     *
     * 공백일 수 없으며, ACTIVE, INACTIVE, SUSPENDED 중 하나의 값을 사용한다.
     */
    @NotBlank(message = "상태는 필수입니다.")
    private String status;
}