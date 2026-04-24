package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupAdminResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final String phone;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;

    public SignupAdminResponseDto(Long id, String email, String name, String phone, String role, String status, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static SignupAdminResponseDto from(Admin admin) {
        return new SignupAdminResponseDto(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getPhone(),
                admin.getRole().getDescription(),
                admin.getStatus().getDescription(),
                admin.getCreatedAt()
        );
    }
}
