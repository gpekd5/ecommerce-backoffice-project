package com.example.ecommercebackofficeproject.admin.repository;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 관리자 엔티티 Repository.
 *
 * 관리자 데이터 조회, 저장, 수정, 삭제 및 조건 검색 기능 제공.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * 이메일 중복 여부 확인.
     *
     * @param email 확인할 이메일
     * @return 이메일 존재 여부
     */
    Boolean existsByEmail(String email);

    /**
     * 이메일 기준 삭제되지 않은 관리자 조회.
     *
     * 로그인 및 관리자 계정 확인 시 사용한다.
     * 소프트 삭제된 관리자는 조회 대상에서 제외한다.
     *
     * @param email 조회할 관리자 이메일
     * @return 삭제되지 않은 관리자 엔티티 Optional 객체
     */
    Optional<Admin> findByEmailAndDeletedAtIsNull(String email);

    /**
     * 아이디 기준 삭제되지 않은 관리자 조회.
     *
     * 로그인 및 관리자 계정 확인 시 사용한다.
     * 소프트 삭제된 관리자는 조회 대상에서 제외한다.
     *
     * @param adminId 조회할 관리자 아이디
     * @return 삭제되지 않은 관리자 엔티티 Optional 객체
     */
    Optional<Admin> findByIdAndDeletedAtIsNull(Long adminId);

    /**
     * 관리자 목록 조건 검색.
     *
     * 키워드, 역할, 상태 조건을 기준으로 관리자 목록 조회.
     * 키워드는 관리자 이름 또는 이메일 기준 검색.
     * 페이지네이션 및 정렬 조건 적용.
     *
     * @param keyword 검색 키워드
     * @param role 관리자 역할 필터 조건
     * @param status 관리자 상태 필터 조건
     * @param pageable 페이지네이션 및 정렬 정보
     * @return 조건에 해당하는 관리자 페이지 객체
     */
    @Query("""
        SELECT a FROM Admin a
            WHERE (:keyword IS NULL OR a.name LIKE %:keyword% OR a.email LIKE %:keyword%)
              AND (:role IS NULL OR a.role = :role)
              AND (:status IS NULL OR a.status = :status)
              AND a.deletedAt IS NULL
    """)
    Page<Admin> findAdminsByFilter(
            @Param("keyword") String keyword,
            @Param("role") AdminRole role,
            @Param("status") AdminStatus status,
            Pageable pageable
    );

    Long countByStatus(AdminStatus status);
}