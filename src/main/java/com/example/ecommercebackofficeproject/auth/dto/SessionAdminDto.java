package com.example.ecommercebackofficeproject.auth.dto;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import lombok.Getter;

@Getter
public class SessionAdminDto {

    private final Long adminId;
    private final String adminName;
    private final String adminEmail;
    private final String adminRole;

    public SessionAdminDto(Long adminId, String adminName, String adminEmail, String adminRole) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminRole = adminRole;
    }

    public static SessionAdminDto from(Admin admin) {
        return new SessionAdminDto(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole().name()
        );
    }
}
