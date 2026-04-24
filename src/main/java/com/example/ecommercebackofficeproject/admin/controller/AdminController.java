package com.example.ecommercebackofficeproject.admin.controller;

import com.example.ecommercebackofficeproject.admin.dto.request.SignupAdminRequestDto;
import com.example.ecommercebackofficeproject.admin.dto.response.SignupAdminResponseDto;
import com.example.ecommercebackofficeproject.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/signup")
    public ResponseEntity<SignupAdminResponseDto> signupAdmin(@Valid @RequestBody SignupAdminRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.signupAdmin(request));
    }

}
