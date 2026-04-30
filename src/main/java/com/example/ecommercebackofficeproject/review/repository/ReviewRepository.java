package com.example.ecommercebackofficeproject.review.repository;

import com.example.ecommercebackofficeproject.dashboard.dto.ReviewRatingCountDto;
import com.example.ecommercebackofficeproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 리뷰(Review) 엔티티에 대한 데이터베이스 액세스 처리를 담당하는 리포지토리입니다.
 * 기본적인 CRUD 기능 외에 동적 필터링 검색 및 통계 쿼리 기능을 제공하며,
 * 모든 조회는 논리 삭제(Soft Delete)되지 않은 데이터를 대상으로 합니다.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

     /**
     * 키워드(고객명, 상품명)와 평점을 기반으로 리뷰 목록을 페이징 조회합니다.
     *
     * @param keyword  검색 키워드 (고객명 또는 상품명)
     * @param rating   조회할 평점 (1~5)
     * @param pageable 페이징 및 정렬 정보
     * @return 필터링된 리뷰 엔티티 페이지
     */
    @Query("SELECT r FROM Review r JOIN r.customer c JOIN r.product p JOIN r.order o WHERE (:keyword IS NULL OR c.name LIKE %:keyword% OR p.productName LIKE %:keyword%) AND (:rating IS NULL OR r.rating = :rating) AND (r.deletedAt IS NULL)")
    Page<Review> findAllWithFilters(@Param("keyword") String keyword, @Param("rating") Integer rating, Pageable pageable);

    /**
     * 특정 상품의 평균 평점을 조회합니다.
     *
     * @param productId 상품 고유 식별자
     * @return 평균 평점 (리뷰가 없을 경우 null 반환 가능)
     */
    @Query("SELECT AVG(r.rating) FROM Review r JOIN r.product p WHERE p.productId = :productId")
    Double getAvgRating(@Param("productId") Long productId);

    /**
     * 특정 상품에 등록된 총 리뷰 개수를 조회합니다.
     *
     * @param productId 상품 고유 식별자
     * @return 총 리뷰 개수
     */
    @Query("SELECT COUNT(r) FROM Review r JOIN r.product p WHERE p.productId = :productId")
    Long getReviewCnt(@Param("productId") Long productId);

    /**
     * 특정 상품의 별점별 리뷰 개수 분포를 조회합니다.
     * 결과는 [0]: 별점(Integer), [1]: 개수(Long) 형태의 Object 배열 리스트로 반환됩니다.
     *
     * @param productId 상품 고유 식별자
     * @return 별점 및 개수 분포 리스트
     */
    @Query("SELECT r.rating, COUNT(r) FROM Review r JOIN r.product p WHERE p.productId = :productId GROUP BY r.rating")
    List<Object[]> getReviewDistribution(@Param("productId") Long productId);

    /**
     * 특정 상품의 최신 리뷰 목록을 조회합니다.
     * N+1 문제 방지를 위해 고객(Customer) 정보를 JOIN FETCH로 함께 조회합니다.
     *
     * @param productId 상품 고유 식별자
     * @param pageable  개수 제한을 위한 페이징 객체
     * @return 최신 리뷰 엔티티 리스트
     */
    @Query("SELECT r FROM Review r JOIN FETCH r.customer c WHERE r.product.productId = :productId ORDER BY r.createdAt DESC")
    List<Review> findRecentReviewTop3(@Param("productId") Long productId, Pageable pageable);

    /**
     * 시스템 전체 리뷰의 평균 평점을 조회합니다. (리뷰가 없으면 0 반환)
     *
     * @return 전체 평균 평점
     */
    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r")
    Double findAverageRating();

    /**
     * 시스템 전체 리뷰의 별점별 분포 현황을 조회합니다.
     *
     * @return 별점별 개수 DTO 리스트
     */
    @Query("SELECT r.rating AS rating, COUNT(r) AS count " +
            "FROM Review r " +
            "WHERE r.deletedAt IS NULL " +
            "GROUP BY r.rating " +
            "ORDER BY r.rating ASC")
    List<ReviewRatingCountDto> countGroupByRating();
}