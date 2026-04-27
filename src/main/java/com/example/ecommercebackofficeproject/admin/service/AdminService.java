package com.example.ecommercebackofficeproject.admin.service;

import com.example.ecommercebackofficeproject.admin.dto.request.SignupAdminRequestDto;
import com.example.ecommercebackofficeproject.admin.dto.response.SignupAdminResponseDto;
import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.global.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupAdminResponseDto signupAdmin(SignupAdminRequestDto request) {

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Admin admin = new Admin(
                request.getEmail(),
                encodedPassword,
                request.getName(),
                request.getPhone(),
                request.getRole(),
                AdminStatus.ACTIVE
        );

        Admin saveAdmin = adminRepository.save(admin);

        return SignupAdminResponseDto.from(saveAdmin);
    }

}
