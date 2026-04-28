package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

/**
 * 관리자 정보 수정 응답 DTO.
 *
 * 관리자 정보 수정 후 관리자 ID, 이름, 이메일, 전화번호 정보 전달.
 */
@Getter
public class UpdateAdminResponseDto {

    /**
     * 관리자 ID.
     */
    private final Long id;

    /**
     * 수정된 관리자 이름.
     */
    private final String name;

    /**
     * 수정된 관리자 이메일.
     */
    private final String email;

    /**
     * 수정된 관리자 전화번호.
     */
    private final String phone;

    /**
     * 관리자 정보 수정 응답 DTO 생성자.
     *
     * @param id 관리자 ID
     * @param name 수정된 관리자 이름
     * @param email 수정된 관리자 이메일
     * @param phone 수정된 관리자 전화번호
     */
    public UpdateAdminResponseDto(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Admin 엔티티를 관리자 정보 수정 응답 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 관리자 정보 수정 응답 DTO
     */
    public static UpdateAdminResponseDto from(Admin admin) {
        return new UpdateAdminResponseDto(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhone()
        );
    }
}