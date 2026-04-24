package com.example.ecommercebackofficeproject.customer.repository;

import com.example.ecommercebackofficeproject.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    @Query(
            value = """
                    SELECT COUNT(*)
                    FROM orders
                    WHERE customer_id = :customerId
                      AND status <> 'CANCELED'
                      AND deleted_at IS NULL
                    """,
            nativeQuery = true
    )
    Long countOrdersByCustomerId(@Param("customerId") Long customerId);

    @Query(
            value = """
                    SELECT COALESCE(SUM(total_price), 0)
                    FROM orders
                    WHERE customer_id = :customerId
                      AND status <> 'CANCELED'
                      AND deleted_at IS NULL
                    """,
            nativeQuery = true
    )
    Long sumTotalOrderAmountByCustomerId(@Param("customerId") Long customerId);

    Optional<Customer> findByIdAndDeletedAtIsNull(Long id);
}
