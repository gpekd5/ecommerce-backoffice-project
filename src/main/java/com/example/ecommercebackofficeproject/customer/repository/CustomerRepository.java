package com.example.ecommercebackofficeproject.customer.repository;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import com.example.ecommercebackofficeproject.dashboard.dto.CustomerStatusCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * 고객 데이터에 대한 DB 접근을 담당하는 Repository.
 *
 * JpaRepository를 통해 기본 CRUD 기능을 제공하고,
 * JpaSpecificationExecutor를 통해 검색, 필터링, 페이징을 위한
 * 동적 쿼리 기능을 사용할 수 있다.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 특정 고객의 총 주문 수를 조회한다.
     *
     * 주문 상태가 CANCELED가 아니고,
     * 논리 삭제되지 않은 주문만 집계한다.
     *
     * @param customerId 고객 ID
     * @return 고객의 총 주문 수
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

    /**
     * 특정 고객의 총 구매 금액을 조회한다.
     *
     * 주문 상태가 CANCELED가 아니고,
     * 논리 삭제되지 않은 주문의 총 금액만 합산한다.
     * 합산 결과가 null인 경우 COALESCE를 통해 0을 반환한다.
     *
     * @param customerId 고객 ID
     * @return 고객의 총 구매 금액
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

    /**
     * 고객 ID로 논리 삭제되지 않은 고객을 조회한다.
     *
     * deletedAt이 null인 고객만 조회하여
     * 삭제 처리된 고객은 조회되지 않도록 한다.
     *
     * @param id 고객 ID
     * @return 조회된 고객 Optional
     */
    Optional<Customer> findByIdAndDeletedAtIsNull(Long id);

    /**
     * 특정 고객 ID를 제외하고 동일한 이메일이 존재하는지 확인한다.
     *
     * 고객 정보 수정 시, 자기 자신의 이메일은 중복 검사에서 제외하고
     * 다른 고객이 이미 사용 중인 이메일인지 확인할 때 사용한다.
     *
     * @param email 확인할 이메일
     * @param id 제외할 고객 ID
     * @return 동일한 이메일이 존재하면 true
     */
    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * 특정 고객 ID를 제외하고 동일한 전화번호가 존재하는지 확인한다.
     *
     * 고객 정보 수정 시, 자기 자신의 전화번호는 중복 검사에서 제외하고
     * 다른 고객이 이미 사용 중인 전화번호인지 확인할 때 사용한다.
     *
     * @param phone 확인할 전화번호
     * @param id 제외할 고객 ID
     * @return 동일한 전화번호가 존재하면 true
     */
    boolean existsByPhoneAndIdNot(String phone, Long id);

    /**
     * 특정 상태를 가진 고객 수를 조회한다.
     *
     * @param customerStatus 조회할 고객 상태
     * @return 해당 상태의 고객 수
     */
    Long countByStatus(CustomerStatus customerStatus);

    /**
     * 고객 상태별 고객 수를 조회한다.
     *
     * 논리 삭제되지 않은 고객만 대상으로 하며,
     * 상태별로 그룹화하여 고객 수를 집계한다.
     *
     * @return 고객 상태별 고객 수 목록
     */
    @Query("SELECT c.status AS status, COUNT(c) AS count " +
            "FROM Customers c " +
            "WHERE c.deletedAt IS NULL " +
            "GROUP BY c.status " +
            "ORDER BY c.status")
    List<CustomerStatusCountDto> countGroupByStatus();
}