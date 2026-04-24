package com.example.ecommercebackofficeproject.admin.repository;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * 이메일 중복 여부 확인
     *
     * @param email 이메일
     * @return 이메일 존재 여부
     */
    Boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);

}
