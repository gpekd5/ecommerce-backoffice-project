package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

/**
 * 관리자 역할 수정 응답 DTO.
 *
 * 관리자 역할 수정 후 관리자 ID와 변경된 역할 정보 전달.
 */
@Getter
public class UpdateAdminRoleResponseDto {

    /**
     * 관리자 ID.
     */
    private final Long id;

    /**
     * 변경된 관리자 역할.
     *
     * Enum 설명값으로 변환된 역할 정보.
     */
    private final String role;

    /**
     * 관리자 역할 수정 응답 DTO 생성자.
     *
     * @param id 관리자 ID
     * @param role 변경된 관리자 역할
     */
    public UpdateAdminRoleResponseDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    /**
     * Admin 엔티티를 관리자 역할 수정 응답 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 관리자 역할 수정 응답 DTO
     */
    public static UpdateAdminRoleResponseDto from(Admin admin) {
        return new UpdateAdminRoleResponseDto(
                admin.getId(),
                admin.getRole().getDescription()
        );
    }
}