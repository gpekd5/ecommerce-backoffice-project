package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 관리자 승인 거부 응답 DTO.
 *
 * 관리자 승인 거부 처리 후 관리자 ID, 상태, 거부일시, 거부사유 정보 전달.
 */
@Getter
public class UpdateAdminRejectResponseDto {

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
     * 관리자 승인 거부일시.
     */
    private final LocalDateTime rejectedAt;

    /**
     * 관리자 승인 거부 사유.
     */
    private final String rejectReason;

    /**
     * 관리자 승인 거부 응답 DTO 생성자.
     *
     * @param id 관리자 ID
     * @param status 변경된 관리자 상태
     * @param rejectedAt 관리자 승인 거부일시
     * @param rejectReason 관리자 승인 거부 사유
     */
    public UpdateAdminRejectResponseDto(Long id, String status, LocalDateTime rejectedAt, String rejectReason) {
        this.id = id;
        this.status = status;
        this.rejectedAt = rejectedAt;
        this.rejectReason = rejectReason;
    }

    /**
     * Admin 엔티티를 관리자 승인 거부 응답 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 관리자 승인 거부 응답 DTO
     */
    public static UpdateAdminRejectResponseDto from(Admin admin) {
        return new UpdateAdminRejectResponseDto(
                admin.getId(),
                admin.getStatus().getDescription(),
                admin.getRejectedAt(),
                admin.getRejectReason()
        );
    }
}