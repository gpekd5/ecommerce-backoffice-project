package com.example.ecommercebackofficeproject.review.repository;

import com.example.ecommercebackofficeproject.dashboard.dto.ReviewRatingCountDto;
import com.example.ecommercebackofficeproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

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
            "AND (:rating IS NULL OR r.rating = :rating) AND " +
            "(r.deletedAt IS NULL)")
    Page<Review> findAllWithFilters(@Param("keyword") String keyword, @Param("rating") int rating, Pageable pageable);

    /**
     * 특정 상품의 평균 평점과 전체 리뷰 개수를 조회합니다.
     * <p>
     * DB의 집계 함수(AVG, COUNT)를 사용하여 성능을 최적화하며,
     * 결과는 [평균 평점(Double), 전체 개수(Long)] 형태의 배열로 반환됩니다.
     *
     * @param productId 상품 고유 식별자
     * @return [0]: 평균 평점, [1]: 전체 리뷰 개수를 담은 Object 배열
     */
    @Query("SELECT AVG(r.rating), COUNT(r) FROM Review r JOIN r.product p WHERE p.productId = :productId")
    Object[] getBasicStats(@Param("productId") Long productId);

    /**
     * 특정 상품의 별점별 리뷰 개수 분포를 조회합니다.
     * <p>
     * 별점(rating)으로 그룹화하여 각 별점당 리뷰 수를 카운트합니다.
     * 결과는 [[별점, 개수], [별점, 개수]...] 형태의 리스트로 반환됩니다.
     *
     * @param productId 상품 고유 식별자
     * @return 별점과 해당 별점의 개수를 담은 리스트 (Object[] 내부: [0]=별점, [1]=개수)
     */
    @Query("SELECT r.rating, COUNT(r) FROM Review r JOIN r.product p WHERE p.productId = :productId GROUP BY r.rating")
    List<Object[]> getReviewDistribution(@Param("productId") Long productId);

    /**
     * 특정 상품의 최신 리뷰 목록을 작성일 내림차순으로 조회합니다.
     * <p>
     * N+1 문제를 방지하기 위해 작성자(Customer) 정보를 JOIN FETCH로 한 번에 가져옵니다.
     * 서비스 레이어에서 org.springframework.data.domain.Pageable을 전달하여
     * 조회 개수를 제한(예: 3개)하는 것을 권장합니다.
     *
     * @param productId 상품 고유 식별자
     * @param pageable  개수 제한 및 페이징 처리를 위한 객체 (예: PageRequest.of(0, 3))
     * @return 최신 리뷰 엔티티 리스트
     */
    @Query("SELECT r FROM Review r JOIN FETCH r.customer c WHERE r.product.productId = :productId ORDER BY r.createdAt DESC")
    List<Review> findRecentReviewTop3(@Param("productId") Long productId, Pageable pageable);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r")
    Double findAverageRating();

    @Query("SELECT r.rating AS rating, COUNT(r) AS count " +
            "FROM Review r " +
            "WHERE r.deletedAt IS NULL " +
            "GROUP BY r.rating " +
            "ORDER BY r.rating ASC")
    List<ReviewRatingCountDto> countGroupByRating();
}
