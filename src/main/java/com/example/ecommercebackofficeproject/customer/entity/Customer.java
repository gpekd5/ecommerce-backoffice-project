package com.example.ecommercebackofficeproject.customer.entity;

import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import com.example.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 고객 정보를 저장하는 엔티티.
 *
 * 고객의 이름, 이메일, 전화번호, 상태 정보를 관리하며,
 * BaseEntity를 상속하여 생성일, 수정일, 삭제일 정보를 함께 관리한다.
 */
@Getter
@Entity(name = "Customers")
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    /**
     * 고객 고유 식별자.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 고객 이름.
     *
     * 필수값이며 최대 50자까지 저장한다.
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * 고객 이메일.
     *
     * 필수값이며 중복될 수 없다.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * 고객 전화번호.
     *
     * 필수값이며 중복될 수 없다.
     */
    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    /**
     * 고객 상태.
     *
     * ACTIVE, INACTIVE, SUSPENDED 중 하나의 값을 가진다.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CustomerStatus status;

    /**
     * 고객 엔티티를 생성한다.
     *
     * 신규 고객은 기본적으로 ACTIVE 상태로 생성된다.
     *
     * @param email 고객 이메일
     * @param name 고객 이름
     * @param phone 고객 전화번호
     * @param status 고객 상태
     */
    public Customer(String email, String name, String phone, CustomerStatus status) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.status = CustomerStatus.ACTIVE;
    }

    /**
     * 고객 기본 정보를 수정한다.
     *
     * 고객의 이름, 이메일, 전화번호를 변경한다.
     *
     * @param name 변경할 고객 이름
     * @param email 변경할 고객 이메일
     * @param phone 변경할 고객 전화번호
     */
    public void updateInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * 고객 상태를 변경한다.
     *
     * 고객의 상태를 ACTIVE, INACTIVE, SUSPENDED 중 하나로 변경한다.
     *
     * @param status 변경할 고객 상태
     */
    public void changeStatus(CustomerStatus status) {
        this.status = status;
    }
}