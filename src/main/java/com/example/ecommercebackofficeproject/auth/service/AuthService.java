package com.example.ecommercebackofficeproject.auth.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.auth.dto.LoginRequestDto;
import com.example.ecommercebackofficeproject.auth.dto.LoginResponseDto;
import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.auth.jwt.JwtProvider;
import com.example.ecommercebackofficeproject.global.exception.ForbiddenException;
import com.example.ecommercebackofficeproject.global.exception.UnauthorizedException;
import com.example.ecommercebackofficeproject.global.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 인증 비즈니스 로직 처리 서비스.
 *
 * 관리자 로그인, 비밀번호 검증, 계정 상태 검증 기능 처리.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * 관리자 데이터 접근 Repository.
     */
    private final AdminRepository adminRepository;

    /**
     * 비밀번호 암호화 및 검증 처리 객체.
     */
    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    /**
     * 관리자 로그인 처리.
     *
     * 이메일 기준 관리자 조회 후 비밀번호 일치 여부 검증.
     * 관리자 계정 상태 검증 후 세션 저장용 관리자 정보 반환.
     *
     * @param request 관리자 로그인 요청 DTO
     * @return 세션 저장용 관리자 DTO
     */
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request) {

        Admin admin = adminRepository.findByEmailAndDeletedAtIsNull(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        validateAdminStatus(admin);

        SessionAdminDto sessionAdmin = SessionAdminDto.from(admin);

        String accessToken = jwtProvider.creatToken(sessionAdmin);

        return new LoginResponseDto(accessToken);
    }

    /**
     * 관리자 계정 상태 검증.
     *
     * 활성 상태가 아닌 경우 계정 상태에 따른 예외 발생.
     *
     * @param admin 관리자 엔티티
     */
    private void validateAdminStatus(Admin admin) {
        switch (admin.getStatus()) {
            case ACTIVE:
                return;

            case PENDING:
                throw new ForbiddenException("계정이 승인대기 상태입니다.");

            case REJECTED:
                throw new ForbiddenException("계정 신청이 거부되었습니다. 사유: " + admin.getRejectReason());

            case SUSPENDED:
                throw new ForbiddenException("계정이 정지되었습니다.");

            case INACTIVE:
                throw new ForbiddenException("계정이 비활성화되었습니다.");

            default:
                throw new ForbiddenException("로그인할 수 없는 계정 상태입니다.");
        }
    }
}