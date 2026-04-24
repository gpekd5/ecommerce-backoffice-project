package com.example.ecommercebackofficeproject.product.entity;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.global.common.BaseEntity;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 상품 정보를 관리하는 엔티티 클래스입니다.
 * 관리자(Admin)와 다대일(N:1) 연관관계를 가집니다.
 */
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity {

    /** 상품 고유 식별자 (PK) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long productId;

    /** 상품명 (필수값) */
    @Column(name = "name", nullable = false)
    private String productName;

    /** 카테고리 (필수값) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    /** 가격 (필수값) */
    @Column(nullable = false)
    private Long price;

    /** 재고 (필수값) */
    @Column(nullable = false)
    private int stock;

    /** 상태 (필수값) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    /** 상품을 등록한 관리자 정보 (N:1 연관관계, 지연 로딩) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    /**
     * 상품의 내용을 수정합니다.
     * @param name 새로운 상품명
     * @param category 변경할 카테고리
     * @param price 새로운 가격
     * @return 수정된 Product 객체
     */
    public Product updateInfo(String name, ProductCategory category, Long price) {
        this.productName = name;
        this.category = category;
        this.price = price;
        return this;
    }
}
