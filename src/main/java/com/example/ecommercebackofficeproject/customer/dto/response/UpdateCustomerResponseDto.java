package com.example.ecommercebackofficeproject.customer.dto.response;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Getter;

/**
 * 고객 정보 수정 응답 데이터를 담는 DTO.
 *
 * 고객 정보 수정 완료 후 변경된 고객 정보를 응답할 때 사용한다.
 */
@Getter
public class UpdateCustomerResponseDto {

    /**
     * 고객 ID.
     */
    private final Long id;

    /**
     * 고객 이름.
     */
    private final String name;

    /**
     * 고객 이메일.
     */
    private final String email;

    /**
     * 고객 전화번호.
     */
    private final String phone;

    /**
     * 고객 상태.
     */
    private final CustomerStatus status;

    /**
     * 고객 정보 수정 응답 DTO를 생성한다.
     *
     * @param id 고객 ID
     * @param name 고객 이름
     * @param email 고객 이메일
     * @param phone 고객 전화번호
     * @param status 고객 상태
     */
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

    /**
     * Customer 엔티티를 고객 정보 수정 응답 DTO로 변환한다.
     *
     * @param customer 고객 엔티티
     * @return 고객 정보 수정 응답 DTO
     */
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