package com.example.ecommercebackofficeproject.auth.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String accessToken;

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
