package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 관리자 승인 응답 DTO.
 *
 * 관리자 승인 처리 후 관리자 ID, 상태, 승인일시 정보 전달.
 */
@Getter
public class UpdateAdminApproveResponseDto {

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
     * 관리자 승인일시.
     */
    private final LocalDateTime approvedAt;

    /**
     * 관리자 승인 응답 DTO 생성자.
     *
     * @param id 관리자 ID
     * @param status 변경된 관리자 상태
     * @param approvedAt 관리자 승인일시
     */
    public UpdateAdminApproveResponseDto(Long id, String status, LocalDateTime approvedAt) {
        this.id = id;
        this.status = status;
        this.approvedAt = approvedAt;
    }

    /**
     * Admin 엔티티를 관리자 승인 응답 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 관리자 승인 응답 DTO
     */
    public static UpdateAdminApproveResponseDto from(Admin admin) {
        return new UpdateAdminApproveResponseDto(
                admin.getId(),
                admin.getStatus().getDescription(),
                admin.getApprovedAt()
        );
    }
}