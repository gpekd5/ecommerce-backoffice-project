package com.example.ecommercebackofficeproject.admin.dto.request;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class SignupAdminRequestDto {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자 이하로 입력해주세요.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private final String password;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private final String name;

    @NotBlank(message = "전화번호는 필수 입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-0000-0000 형식이어야 합니다.")
    private final String phone;

    @NotNull(message = "관리자 역할은 필수값입니다.")
    private final AdminRole role;

    public SignupAdminRequestDto(String email, String password, String name, String phone, AdminRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
