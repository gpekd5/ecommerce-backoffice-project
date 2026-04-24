package com.example.ecommercebackofficeproject.product.repository;

import com.example.ecommercebackofficeproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
