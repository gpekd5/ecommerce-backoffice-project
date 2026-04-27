package com.example.ecommercebackofficeproject.customer.entity;

import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import com.example.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity(name = "Customers")
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {
    /** 고객 고유 식별자 (PK)*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 고객 이름(필수값)*/
    @Column(nullable = false, length = 50)
    private String name;

    /** 고객 이메일(필수값)*/
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /** 고객 전화번호(필수값)*/
    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    /*
     * 고객 상태
     * ACTIVE: 활성
     * INACTIVE: 비활성
     * SUSPENDED: 정지
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CustomerStatus status;

    public Customer(String email, String name, String phone, CustomerStatus status) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.status = CustomerStatus.ACTIVE;
    }

    /*
     * 고객 상태 변경 메서드
     *
     * 고객의 상태(ACTIVE, INACTIVE, SUSPENDED)를 변경한다.
     */
    public void updateInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    /*
     * 고객 삭제 메서드 (논리 삭제)
     *
     * BaseEntity의 deletedAt 필드를 활용하여
     * 실제 데이터를 삭제하지 않고 삭제 처리한다.
     */
    public void changeStatus(CustomerStatus status) {
        this.status = status;
    }
}
