package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

/**
 * 관리자 상태 수정 응답 DTO.
 *
 * 관리자 상태 수정 후 관리자 ID와 변경된 상태 정보 전달.
 */
@Getter
public class UpdateAdminStatusResponseDto {

    /**
     * 관리자 ID.
     */
    private final Long id;

    /**
     * 변경된 관리자 상태.
     *
     * Enum 설명값으로 변환된 상태 정보.
     */
    private final String status;

    /**
     * 관리자 상태 수정 응답 DTO 생성자.
     *
     * @param id 관리자 ID
     * @param status 변경된 관리자 상태
     */
    public UpdateAdminStatusResponseDto(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Admin 엔티티를 관리자 상태 수정 응답 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 관리자 상태 수정 응답 DTO
     */
    public static UpdateAdminStatusResponseDto from(Admin admin) {
        return new UpdateAdminStatusResponseDto(
                admin.getId(),
                admin.getStatus().getDescription()
        );
    }
}