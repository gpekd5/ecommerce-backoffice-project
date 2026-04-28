package com.example.ecommercebackofficeproject.auth.controller;

import com.example.ecommercebackofficeproject.auth.dto.LoginRequestDto;
import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.auth.service.AuthService;
import com.example.ecommercebackofficeproject.global.exception.BadRequestException;
import com.example.ecommercebackofficeproject.global.response.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 인증 API 요청 처리 컨트롤러.
 *
 * 관리자 로그인 및 로그아웃 기능 담당.
 * 로그인 성공 시 세션에 로그인 관리자 정보 저장.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AuthController {

    /**
     * 관리자 인증 비즈니스 로직 처리 서비스.
     */
    private final AuthService authService;

    /**
     * 관리자 로그인 API.
     *
     * 요청 본문으로 전달받은 로그인 정보 검증 후 관리자 인증 처리.
     * 인증 성공 시 세션에 로그인 관리자 정보 저장.
     *
     * @param request 관리자 로그인 요청 DTO
     * @param session HTTP 세션 객체
     * @return 로그인 처리 결과 응답
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(
            @Valid @RequestBody LoginRequestDto request,
            HttpSession session
    ) {
        SessionAdminDto sessionAdmin = authService.login(request);
        session.setAttribute("loginUser", sessionAdmin);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        HttpStatus.OK,
                        "로그인이 완료되었습니다.",
                        null
                ));
    }

    /**
     * 관리자 로그아웃 API.
     *
     * 세션에 저장된 로그인 관리자 정보 확인 후 세션 무효화 처리.
     * 로그인 정보가 없는 경우 Bad Request 응답 반환.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param session HTTP 세션 객체
     * @return 로그아웃 처리 결과 응답
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @SessionAttribute(name = "loginUser", required = false) SessionAdminDto sessionAdmin,
            HttpSession session
    ) {
        if (sessionAdmin == null) {
            throw new BadRequestException("로그인 정보가 없습니다.");
        }

        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        HttpStatus.OK,
                        "로그아웃이 완료되었습니다.",
                        null
                ));
    }
}