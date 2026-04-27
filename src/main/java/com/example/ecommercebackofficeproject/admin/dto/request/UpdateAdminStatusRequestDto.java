package com.example.ecommercebackofficeproject.admin.dto.request;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateAdminStatusRequestDto {

    /**
     * 관리자 상태.
     *
     * 필수 입력값.
     * 관리자 상태 구분용 역할 정보.
     */
    @NotNull(message = "관리자 역할은 필수값입니다.")
    private final AdminStatus status;

    public UpdateAdminStatusRequestDto(AdminStatus status) {
        this.status = status;
    }
}
