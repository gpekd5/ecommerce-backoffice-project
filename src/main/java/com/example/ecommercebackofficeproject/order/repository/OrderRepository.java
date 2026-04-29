package com.example.ecommercebackofficeproject.order.repository;

import com.example.ecommercebackofficeproject.order.entity.Order;
import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT o
                FROM Order o
                WHERE (:keyword IS NULL OR
                       o.orderNumber LIKE %:keyword% OR 
                       o.customer.name LIKE %:keyword%)
                  AND (:status IS NULL OR o.orderStatus = :status)
            """)
    Page<Order> searchOrders(
            @Param("keyword") String keyword,
            @Param("status") OrderStatus status,
            Pageable pageable
    );

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.orderStatus = :status")
    Long sumTotalRevenue(@Param("status") OrderStatus status);

    long countByOrderStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) " +
            "FROM Order o " +
            "WHERE o.orderStatus = :status " +
            "AND o.createdAt BETWEEN :start AND :end")
    Long sumDailyRevenue(
            @Param("status") OrderStatus orderStatus,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
