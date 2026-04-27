package com.example.ecommercebackofficeproject.admin.dto.request;

import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateAdminRejectRequestDto {

    @NotNull(message = "거부 사유는 필수 입니다.")
    private final String rejectReason;

    public UpdateAdminRejectRequestDto(String rejectReason) {

        this.rejectReason = rejectReason;
    }
}
