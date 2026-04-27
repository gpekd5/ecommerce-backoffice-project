package com.example.ecommercebackofficeproject.review.repository;

import com.example.ecommercebackofficeproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 리뷰(Review) 엔티티에 대한 데이터베이스 액세스 처리를 담당하는 리포지토리입니다.
 * <p>
 * 기본 CRUD 기능 외에 고객,상품명 검색, 평점별 필터링 필터링을 지원하며,
 * 모든 조회는 논리 삭제(Soft Delete)되지 않은 데이터를 대상으로 합니다.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * 키워드(고객명, 상품명)와 평점을 기반으로 리뷰 목록을 페이징 조회합니다.
     * <p>
     * - keyword: 고객명(User.username) 또는 상품명(Product.productName)에 키워드가 포함된 경우 조회합니다.
     * - rating: 특정 평점(1~5)과 일치하는 리뷰만 필터링합니다. (null일 경우 전체 조회)
     * - 성능 최적화: 연관된 주문(Order), 고객(User), 상품(Product) 정보를 한 번에 가져오기 위해 JOIN FETCH를 고려합니다.
     *
     * @param keyword  검색 키워드 (고객명 또는 상품명, null 가능)
     * @param rating   평점 필터 (null 가능)
     * @param pageable 페이징 및 정렬 정보
     * @return 필터링된 리뷰 엔티티 페이지
     */
    @Query("SELECT r FROM Review r " +
            "JOIN r.customer c " +
            "JOIN r.product p " +
            "JOIN r.order o " +
            "WHERE (:keyword IS NULL OR c.name LIKE %:keyword% OR p.productName LIKE %:keyword%) " +
            "AND (:rating IS NULL OR r.rating = :rating)")
    Page<Review> findAllWithFilters(@Param("keyword") String keyword, @Param("rating") int rating, Pageable pageable);
}
