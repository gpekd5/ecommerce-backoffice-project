package com.example.ecommercebackofficeproject.product.repository;

import com.example.ecommercebackofficeproject.dashboard.dto.ProductCategoryCountDto;
import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 상품(Product) 엔티티에 대한 데이터베이스 액세스 처리를 담당하는 리포지토리 인터페이스입니다.
 * Spring Data JPA의 기본 CRUD 기능을 상속받으며,
 * 상품명 검색, 카테고리/상태별 필터링, 재고 및 카테고리 통계 기능을 제공합니다.
 * 모든 조회는 기본적으로 논리 삭제(Soft Delete)되지 않은 데이터를 대상으로 설계되었습니다.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * 다중 필터 조건(키워드, 카테고리, 상태)을 적용하여 삭제되지 않은 상품 목록을 페이징 조회합니다.
     * - keyword: 상품명(productName)에 키워드가 포함된 경우 (Partial Match)
     * - category: 특정 카테고리에 속한 경우
     * - status: 특정 판매 상태(판매중, 품절 등)인 경우
     * - 삭제 조건: deletedAt 필드가 null인 유효한 상품만 조회
     *
     * @param keyword  상품명 검색어 (null 또는 빈 문자열일 경우 조건 무시)
     * @param category 조회할 상품 카테고리 (null일 경우 조건 무시)
     * @param status   조회할 상품 상태 (null일 경우 조건 무시)
     * @param pageable 페이징 및 정렬 정보
     * @return         필터링된 조건에 부합하는 페이징된 상품 엔티티 목록
     */
    @Query("SELECT p FROM Product p WHERE (:keyword IS NULL OR p.productName LIKE %:keyword%) AND (:category IS NULL OR p.category = :category) AND (:status IS NULL OR p.status = :status) AND (p.deletedAt IS NULL)")
    Page<Product> findAllWithFilters(@Param("keyword") String keyword, @Param("category") ProductCategory category, @Param("status") ProductStatus status, Pageable pageable);

    /**
     * 지정된 수량 이하의 재고를 보유한 상품의 총 개수를 조회합니다.
     * 주로 '재고 부족' 알림이나 대시보드 통계용으로 사용됩니다.
     * @param stock 기준 재고 수량
     * @return      기준 수량 이하인 상품의 전체 수
     */
    Long countByStockLessThanEqual(Integer stock);

    /**
     * 특정 재고 수량과 정확히 일치하는 상품의 총 개수를 조회합니다.
     * 주로 '품절(재고 0)' 상태의 상품 수를 파악할 때 활용됩니다.
     * @param stock 조회할 재고 수량 (예: 0)
     * @return      해당 재고 수량을 가진 상품의 전체 수
     */
    Long countByStock(Integer stock);

    /**
     * 전체 상품을 카테고리별로 그룹화하여 상품 수를 집계합니다.
     * 삭제되지 않은 모든 상품에 대해 카테고리별 분포 현황을 제공합니다.
     * @return 카테고리명과 해당 카테고리의 상품 수를 담은 DTO 리스트
     */
    @Query("SELECT p.category AS category, COUNT(p) AS count FROM Product p GROUP BY p.category")
    List<ProductCategoryCountDto> countGroupByCategory();
}
