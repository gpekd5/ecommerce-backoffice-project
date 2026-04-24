package com.example.ecommercebackofficeproject.admin.repository;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
