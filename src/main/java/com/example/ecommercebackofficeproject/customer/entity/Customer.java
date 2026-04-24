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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, unique = true, length = 20)
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CustomerStatus status;

    public Customer(String email, String name, String phone, CustomerStatus status) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.status = CustomerStatus.ACTIVE;
    }

    public void updateInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public void changeStatus(CustomerStatus status) {
        this.status = status;
    }
}
