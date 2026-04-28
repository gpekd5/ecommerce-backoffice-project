package com.example.ecommercebackofficeproject.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 관리자 승인 거부 요청 DTO.
 *
 * 관리자 승인 거부 시 거부 사유 전달.
 */
@Getter
public class UpdateAdminRejectRequestDto {

    /**
     * 관리자 승인 거부 사유.
     *
     * 필수 입력값.
     * 최대 100자 제한.
     */
    @NotBlank(message = "거부 사유는 필수입니다.")
    @Size(max = 100, message = "거부 사유는 100자 이하로 입력해주세요.")
    private final String rejectReason;

    /**
     * 관리자 승인 거부 요청 DTO 생성자.
     *
     * @param rejectReason 관리자 승인 거부 사유
     */
    public UpdateAdminRejectRequestDto(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}