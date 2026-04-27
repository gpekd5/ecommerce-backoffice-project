package com.example.ecommercebackofficeproject.review.entity;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.global.common.BaseEntity;
import com.example.ecommercebackofficeproject.order.entity.Order;
import com.example.ecommercebackofficeproject.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 리뷰 정보를 관리하는 엔티티 클래스입니다.
 * 하나의 주문(Order)당 하나의 리뷰만 작성 가능하며(1:1),
 * 특정 상품(Product)과 작성자(Customer)에 대한 정보를 참조합니다.
 */
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
public class Review extends BaseEntity {

    /** 리뷰 고유 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long reviewId;

    /** 리뷰 내용 (필수값, 최대 1000자) */
    @Column(nullable = false, length = 1000)
    private String comment;

    /** 평점 (필수값, 최솟값: 1, 최댓값: 5) */
    @Column(nullable = false)
    @Min(1) @Max(5)
    private int rating;

    /**
     * 리뷰와 연결된 주문 정보입니다.
     * 한 주문당 리뷰는 하나만 존재해야 하므로 unique 제약 조건이 설정되어 있습니다.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

    /** 리뷰 대상이 되는 상품 정보입니다. (N:1 연관관계) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /** 리뷰를 작성한 고객 정보입니다. (N:1 연관관계) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
