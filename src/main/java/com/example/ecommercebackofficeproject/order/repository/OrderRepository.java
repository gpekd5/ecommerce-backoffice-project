package com.example.ecommercebackofficeproject.order.repository;

import com.example.ecommercebackofficeproject.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public long countByCreatedAtBetween(LocalDateTime start,LocalDateTime end);
}
