package com.example.ecommercebackofficeproject.order.entity;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.global.common.BaseEntity;
import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import com.example.ecommercebackofficeproject.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    private String orderCancelReason;

    @Builder
    public Order(String orderNumber, Customer customer, Product product, Integer quantity, Admin admin) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice().intValue() * quantity;
        this.orderStatus = OrderStatus.PREPARING;
        this.admin = admin;
        this.orderCancelReason = null;
    }

    private void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


}
