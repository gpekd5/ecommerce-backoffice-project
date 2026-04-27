package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateAdminApproveResponseDto {

    private final Long id;
    private final String status;
    private final LocalDateTime approvedAt;

    public UpdateAdminApproveResponseDto(Long id, String status, LocalDateTime approvedAt) {
        this.id = id;
        this.status = status;
        this.approvedAt = approvedAt;
    }

    public static UpdateAdminApproveResponseDto from(Admin admin) {
        return new UpdateAdminApproveResponseDto(
                admin.getId(),
                admin.getStatus().getDescription(),
                admin.getApprovedAt()
        );
    }

}
