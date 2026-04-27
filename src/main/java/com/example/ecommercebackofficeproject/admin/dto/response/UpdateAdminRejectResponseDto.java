package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateAdminRejectResponseDto {

    private final Long id;
    private final String status;
    private final LocalDateTime rejectedAt;
    private final String rejectReason;

    public UpdateAdminRejectResponseDto(Long id, String status, LocalDateTime rejectedAt, String rejectReason) {
        this.id = id;
        this.status = status;
        this.rejectedAt = rejectedAt;
        this.rejectReason = rejectReason;
    }

    public static UpdateAdminRejectResponseDto from(Admin admin) {
        return new UpdateAdminRejectResponseDto(
                admin.getId(),
                admin.getStatus().getDescription(),
                admin.getRejectedAt(),
                admin.getRejectReason()
        );
    }

}
