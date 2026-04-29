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

    /**
     * 관리자에 의해 상품 상태를 수동으로 변경합니다.
     */
    public Product updateStatus(ProductStatus status) {
        // 1. 재고 0인데 판매 시작하려는 경우
        if(this.stock <= 0 && status == ProductStatus.AVAILABLE) {
            throw new IllegalArgumentException("재고가 없는 상품은 판매 중 상태로 변경할 수 없습니다.");
        }

        // 2. 재고가 있는데 굳이 '품절'로 바꾸려는 경우
        if(this.stock > 0 && status == ProductStatus.SOLD_OUT) {
            throw new IllegalArgumentException("재고가 남아있어 품절 상태로 변경할 수 없습니다. 대신 단종을 이용하세요.");
        }

        this.status = status;

        return this;
    }

    /**
     * 재고 수량에 기초하여 상품 상태를 자동으로 업데이트합니다.
     * 단종(DISCONTINUED) 상태는 시스템이 마음대로 변경하지 못하도록 방어합니다.
     */
    public void autoUpdateStatus() {
        // 단종 상태라면 시스템이 건드리지 않고 그대로 유지
        if(this.status == ProductStatus.DISCONTINUED) {
            return;
        }

        // 재고에 따른 상태 결정
        if(this.stock <= 0) {
            this.status = ProductStatus.SOLD_OUT;
        } else {
            this.status = ProductStatus.AVAILABLE;
        }
    }

    /**
     * 관리자에 의해 상품의 재고 수량을 변경합니다.
     * 변경 후 현재 재고 상황에 맞게 상태를 재검증합니다.
     */
    public void updateStock(int quantity) {
        // 1. 현 재고에 변경량을 대입
        int updatedStock = this.stock + quantity;

        // 2. 변경된 재고량이 음수인지 확인
        if (updatedStock < 0) {
            throw new IllegalArgumentException("재고는 0보다 작을 수 없습니다.");
        }

        // 3. 변경점 반영 및 상태 업데이트
        this.stock = updatedStock;
        this.autoUpdateStatus();
    }

}
