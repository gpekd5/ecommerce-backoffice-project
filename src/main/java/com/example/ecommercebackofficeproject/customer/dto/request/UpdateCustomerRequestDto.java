package com.example.ecommercebackofficeproject.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * 고객 정보 수정 요청 데이터를 담는 DTO.
 *
 * 고객의 이름, 이메일, 전화번호를 수정할 때 사용한다.
 */
@Getter
public class UpdateCustomerRequestDto {

    /**
     * 수정할 고객 이름.
     *
     * 공백일 수 없다.
     */
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    /**
     * 수정할 고객 이메일.
     *
     * 공백일 수 없으며, 이메일 형식이어야 한다.
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    /**
     * 수정할 고객 전화번호.
     *
     * 010-XXXX-XXXX 형식이어야 한다.
     */
    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(
            regexp = "^010-\\d{4}-\\d{4}$",
            message = "전화번호는 010-XXXX-XXXX 형식이어야 합니다."
    )
    private String phone;
}