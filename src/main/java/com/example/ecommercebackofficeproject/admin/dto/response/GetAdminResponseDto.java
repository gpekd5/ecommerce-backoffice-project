package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 관리자 단건 조회 응답 DTO.
 *
 * 관리자 ID, 이메일, 이름, 전화번호, 역할, 상태, 생성일시, 승인일시 정보 전달.
 */
@Getter
public class GetAdminResponseDto {

    /**
     * 관리자 ID.
     */
    private final Long id;

    /**
     * 관리자 이메일.
     */
    private final String email;

    /**
     * 관리자 이름.
     */
    private final String name;

    /**
     * 관리자 전화번호.
     */
    private final String phone;

    /**
     * 관리자 역할.
     *
     * Enum 설명값으로 변환된 역할 정보.
     */
    private final String role;

    /**
     * 관리자 상태.
     *
     * Enum 설명값으로 변환된 상태 정보.
     */
    private final String status;

    /**
     * 관리자 생성일시.
     */
    private final LocalDateTime createdAt;

    /**
     * 관리자 승인일시.
     */
    private final LocalDateTime approvedAt;

    /**
     * 관리자 단건 조회 응답 DTO 생성자.
     *
     * @param id 관리자 ID
     * @param email 관리자 이메일
     * @param name 관리자 이름
     * @param phone 관리자 전화번호
     * @param role 관리자 역할
     * @param status 관리자 상태
     * @param createdAt 관리자 생성일시
     * @param approvedAt 관리자 승인일시
     */
    public GetAdminResponseDto(
            Long id, String email, String name, String phone, String role, String status,
            LocalDateTime createdAt, LocalDateTime approvedAt
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
    }

    /**
     * Admin 엔티티를 관리자 단건 조회 응답 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 관리자 단건 조회 응답 DTO
     */
    public static GetAdminResponseDto from(Admin admin) {
        return new GetAdminResponseDto(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getPhone(),
                admin.getRole().getDescription(),
                admin.getStatus().getDescription(),
                admin.getCreatedAt(),
                admin.getApprovedAt()
        );
    }
}
