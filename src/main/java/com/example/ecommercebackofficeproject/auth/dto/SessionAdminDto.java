package com.example.ecommercebackofficeproject.auth.dto;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

/**
 * 세션 저장용 관리자 DTO.
 *
 * 로그인 성공 시 세션에 저장할 관리자 기본 정보 전달.
 * 인증 및 권한 확인에 필요한 최소 정보만 포함.
 */
@Getter
public class SessionAdminDto {

    /**
     * 관리자 ID.
     */
    private final Long adminId;

    /**
     * 관리자 이름.
     */
    private final String adminName;

    /**
     * 관리자 이메일.
     */
    private final String adminEmail;

    /**
     * 관리자 역할.
     *
     * Enum name 값으로 저장된 역할 정보.
     */
    private final String adminRole;

    /**
     * 세션 저장용 관리자 DTO 생성자.
     *
     * @param adminId 관리자 ID
     * @param adminName 관리자 이름
     * @param adminEmail 관리자 이메일
     * @param adminRole 관리자 역할
     */
    public SessionAdminDto(Long adminId, String adminName, String adminEmail, String adminRole) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminRole = adminRole;
    }

    /**
     * Admin 엔티티를 세션 저장용 관리자 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 세션 저장용 관리자 DTO
     */
    public static SessionAdminDto from(Admin admin) {
        return new SessionAdminDto(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole().name()
        );
    }
}