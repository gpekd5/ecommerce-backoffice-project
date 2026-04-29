package com.example.ecommercebackofficeproject.customer.repository;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import com.example.ecommercebackofficeproject.dashboard.dto.CustomerStatusCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * Customer Repository :
 *  - 고객 데이터에 대한 DB 접근을 담당
 *
 * JpaRepository:
 *  - 기본 CRUD 제공 (save, findById, findAll 등)
 *
 * JpaSpecificationExecutor:
 *  - 동적 쿼리 (검색, 필터링, 페이징) 처리 가능
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    /*
     * 특정 고객의 총 주문 수 조회
     *
     * 주문 상태가 CANCELED가 아닌 경우만 집계하며,
     * 삭제되지 않은 주문만 대상으로 한다.
     *
     * nativeQuery 사용 이유:
     * - 집계 성능 및 단순 SQL 작성
     *
     * @param customerId 고객 ID
     * @return 주문 수
     */
    @Query(
            value = """
                    SELECT COUNT(*)
                    FROM orders
                    WHERE customer_id = :customerId
                      AND order_status <> 'CANCELED'
                      AND deleted_at IS NULL
                    """,
            nativeQuery = true
    )
    Long countOrdersByCustomerId(@Param("customerId") Long customerId);

    /*
     * 특정 고객의 총 구매 금액 조회
     *
     * 주문 상태가 CANCELED가 아닌 경우만 합산하며,
     * 삭제되지 않은 주문만 대상으로 한다.
     *
     * COALESCE:
     * - 결과가 null일 경우 0으로 반환
     *
     * @param customerId 고객 ID
     * @return 총 구매 금액
     */
    @Query(
            value = """
                    SELECT COALESCE(SUM(total_price), 0)
                    FROM orders
                    WHERE customer_id = :customerId
                      AND order_status <> 'CANCELED'
                      AND deleted_at IS NULL
                    """,
            nativeQuery = true
    )
    Long sumTotalOrderAmountByCustomerId(@Param("customerId") Long customerId);

    /*
     * 고객 단건 조회 (논리 삭제 제외)
     *
     * deletedAt이 null인 데이터만 조회하여
     * 실제 삭제된 고객은 조회되지 않도록 처리
     *
     * @param id 고객 ID
     * @return Optional<Customer>
     */
    Optional<Customer> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    Long countByStatus(CustomerStatus customerStatus);

    @Query("SELECT c.status AS status, COUNT(c) AS count " +
            "FROM Customers c " +
            "WHERE c.deletedAt IS NULL " +
            "GROUP BY c.status " +
            "ORDER BY c.status")
    List<CustomerStatusCountDto> countGroupByStatus();
}
