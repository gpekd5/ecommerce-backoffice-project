package com.example.ecommercebackofficeproject.customer.dto.response;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 고객 상세 조회 응답 데이터를 담는 DTO.
 *
 * 고객 기본 정보와 함께 총 주문 수, 총 구매 금액을 응답할 때 사용한다.
 */
@Getter
public class GetCustomerDetailResponseDto {

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
     * 고객 생성 일시.
     */
    private final LocalDateTime createdAt;

    /**
     * 고객의 총 주문 수.
     */
    private final Long totalOrderCount;

    /**
     * 고객의 총 구매 금액.
     */
    private final Long totalOrderAmount;

    /**
     * 고객 상세 조회 응답 DTO를 생성한다.
     *
     * @param id 고객 ID
     * @param name 고객 이름
     * @param email 고객 이메일
     * @param phone 고객 전화번호
     * @param status 고객 상태
     * @param createdAt 고객 생성 일시
     * @param totalOrderCount 총 주문 수
     * @param totalOrderAmount 총 구매 금액
     */
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

    /**
     * Customer 엔티티와 주문 통계 정보를 고객 상세 조회 응답 DTO로 변환한다.
     *
     * @param customer 고객 엔티티
     * @param totalOrderCount 총 주문 수
     * @param totalOrderAmount 총 구매 금액
     * @return 고객 상세 조회 응답 DTO
     */
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