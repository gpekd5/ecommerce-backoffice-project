package com.example.ecommercebackofficeproject.customer.repository;

import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerResponse;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /*@Query("""
        SELECT new com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerResponse(
            c.id,
            c.name,
            c.email,
            c.phone,
            c.status,
            c.createdAt,
            COUNT(o.id),
            COALESCE(SUM(o.totalPrice), 0)
        )
        FROM Customer c
        LEFT JOIN Order o ON o.customer = c
        WHERE (:keyword IS NULL OR c.name LIKE CONCAT('%', :keyword, '%') OR c.email LIKE CONCAT('%', :keyword, '%'))
          AND (:status IS NULL OR c.status = :status)
          AND c.deletedAt IS NULL
        GROUP BY c.id, c.name, c.email, c.phone, c.status, c.createdAt
    """)*/

    Page<GetCustomerResponse> searchCustomers(@Param("keyword")String keyword,
                                              @Param("status")CustomerStatus customerStatus,
                                              Pageable pageable
    );
}
