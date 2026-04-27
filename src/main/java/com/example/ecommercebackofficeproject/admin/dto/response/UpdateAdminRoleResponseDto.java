package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

@Getter
public class UpdateAdminRoleResponseDto {

    private final Long id;
    private final String role;

    public UpdateAdminRoleResponseDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public static UpdateAdminRoleResponseDto from(Admin admin) {
        return new UpdateAdminRoleResponseDto(
                admin.getId(),
                admin.getRole().getDescription()
        );
    }
}
