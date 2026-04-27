package com.example.ecommercebackofficeproject.product.repository;

import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 상품(Product) 엔티티에 대한 데이터베이스 액세스 처리를 담당하는 리포지토리입니다.
 * <p>
 * 기본 CRUD 기능 외에 상품명 검색, 카테고리별 필터링, 판매 상태별 필터링을 지원하며,
 * 모든 조회는 논리 삭제(Soft Delete)되지 않은 데이터를 대상으로 합니다.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 검색 키워드, 카테고리, 상품 상태를 기반으로 삭제되지 않은 상품 목록을 페이징 조회합니다.
     * <p>
     * - keyword: 상품명에 해당 키워드가 포함된 상품을 검색합니다. (LIKE %keyword%)
     * - category: 지정된 카테고리와 일치하는 상품을 필터링합니다.
     * - status: 지정된 판매 상태와 일치하는 상품을 필터링합니다.
     * - 삭제 여부: {@code deletedAt}이 {@code null}인 데이터(삭제되지 않은 상품)만 조회합니다.
     *
     * @param keyword  상품명 검색어 (null인 경우 조건 무시)
     * @param category 상품 카테고리 (null인 경우 조건 무시)
     * @param status   상품 판매 상태 (null인 경우 조건 무시)
     * @param pageable 페이징 및 정렬 정보
     * @return 필터링 조건에 부합하는 페이징된 상품 엔티티 목록
     */
    @Query("SELECT p FROM Product p WHERE" +
            "(:keyword IS NULL OR p.productName LIKE %:keyword%) AND " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(p.deletedAt IS NULL)")
    Page<Product> findAllWithFilters(@Param("keyword") String keyword, @Param("category") ProductCategory category, @Param("status") ProductStatus status, Pageable pageable);
}
