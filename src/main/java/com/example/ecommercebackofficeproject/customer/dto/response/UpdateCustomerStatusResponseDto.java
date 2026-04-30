package com.example.ecommercebackofficeproject.customer.dto.response;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Getter;

/**
 * 고객 상태 변경 응답 데이터를 담는 DTO.
 *
 * 고객 상태 변경 완료 후 변경된 상태 정보를 응답할 때 사용한다.
 */
@Getter
public class UpdateCustomerStatusResponseDto {

    /**
     * 고객 ID.
     */
    private final Long id;

    /**
     * 변경된 고객 상태.
     */
    private final CustomerStatus status;

    /**
     * 고객 상태 변경 응답 DTO를 생성한다.
     *
     * @param id 고객 ID
     * @param status 변경된 고객 상태
     */
    public UpdateCustomerStatusResponseDto(Long id, CustomerStatus status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Customer 엔티티를 고객 상태 변경 응답 DTO로 변환한다.
     *
     * @param customer 고객 엔티티
     * @return 고객 상태 변경 응답 DTO
     */
    public static UpdateCustomerStatusResponseDto from(Customer customer) {
        return new UpdateCustomerStatusResponseDto(
                customer.getId(),
                customer.getStatus()
        );
    }

}