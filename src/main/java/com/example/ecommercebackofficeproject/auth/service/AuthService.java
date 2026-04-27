package com.example.ecommercebackofficeproject.auth.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.auth.dto.LoginRequestDto;
import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.global.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SessionAdminDto login(LoginRequestDto request) {

        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(),admin.getPassword())) {
            throw new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        vaildateAdminStatus(admin);

        return SessionAdminDto.from(admin);
    }

    private void vaildateAdminStatus(Admin admin) {
        switch (admin.getStatus()) {
            case ACTIVE:
                return;

            case PENDING:
                throw new IllegalStateException("계정이 승인대기 상태입니다.");

            case REJECTED:
                throw new IllegalStateException("계정 신청이 거부되었습니다. 사유: " + admin.getRejectReason());

            case SUSPENDED:
                throw new IllegalStateException("계정이 정지되었습니다.");

            case INACTIVE:
                throw new IllegalStateException("계정이 비활성화되었습니다.");

            default:
                throw new IllegalStateException("로그인할 수 없는 계정 상태입니다.");
        }
    }
}
