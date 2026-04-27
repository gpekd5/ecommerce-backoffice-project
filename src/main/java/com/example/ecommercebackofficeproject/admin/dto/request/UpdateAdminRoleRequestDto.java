package com.example.ecommercebackofficeproject.admin.dto.request;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateAdminRoleRequestDto {

    /**
     * 관리자 역할.
     *
     * 필수 입력값.
     * 관리자 권한 구분용 역할 정보.
     */
    @NotNull(message = "관리자 역할은 필수값입니다.")
    private final AdminRole role;

    public UpdateAdminRoleRequestDto(AdminRole role) {
        this.role = role;
    }
}
