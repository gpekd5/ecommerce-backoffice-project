package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

@Getter
public class UpdateAdminStatusResponseDto {

    private final Long id;
    private final String status;

    public UpdateAdminStatusResponseDto(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public static UpdateAdminStatusResponseDto from(Admin admin) {
        return new UpdateAdminStatusResponseDto(
                admin.getId(),
                admin.getStatus().getDescription()
        );
    }
}
