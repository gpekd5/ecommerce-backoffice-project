package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import lombok.Getter;

@Getter
public class UpdateAdminResponseDto {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;

    public UpdateAdminResponseDto(
            Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public static UpdateAdminResponseDto from(Admin admin) {
        return new UpdateAdminResponseDto(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhone()
        );
    }
}
