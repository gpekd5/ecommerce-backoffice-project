package com.example.ecommercebackofficeproject.customer.type;

/**
 * 고객 상태를 나타내는 Enum.
 *
 * 고객 계정의 현재 사용 가능 여부를 구분할 때 사용한다.
 */
public enum CustomerStatus {

    /**
     * 활성 상태.
     *
     * 정상적으로 서비스를 이용할 수 있는 고객 상태이다.
     */
    ACTIVE,

    /**
     * 비활성 상태.
     *
     * 일시적으로 사용이 제한되거나 비활성 처리된 고객 상태이다.
     */
    INACTIVE,

    /**
     * 정지 상태.
     *
     * 정책 위반 또는 관리자 조치로 인해 이용이 정지된 고객 상태이다.
     */
    SUSPENDED
}