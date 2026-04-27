package com.example.ecommercebackofficeproject.admin.controller;

import com.example.ecommercebackofficeproject.admin.dto.request.SignupAdminRequestDto;
import com.example.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequestDto;
import com.example.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequestDto;
import com.example.ecommercebackofficeproject.admin.dto.request.UpdateAdminStatusRequestDto;
import com.example.ecommercebackofficeproject.admin.dto.response.*;
import com.example.ecommercebackofficeproject.admin.service.AdminService;
import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 관련 API 요청 처리 컨트롤러.
 *
 * 관리자 회원가입, 관리자 목록 조회, 관리자 단건 조회 기능 담당.
 * 인증이 필요한 요청의 경우 세션에 저장된 로그인 관리자 정보 사용.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    /**
     * 관리자 비즈니스 로직 처리 서비스.
     */
    private final AdminService adminService;

    /**
     * 관리자 회원가입 API.
     *
     * 요청 본문으로 전달받은 관리자 회원가입 정보 검증 후
     * 새로운 관리자 계정 생성.
     *
     * @param request 관리자 회원가입 요청 DTO
     * @return 생성된 관리자 정보 응답 DTO
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupAdminResponseDto> signupAdmin(
            @Valid @RequestBody SignupAdminRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.signupAdmin(request));
    }

    /**
     * 관리자 목록 조회 API.
     *
     * 로그인한 관리자 정보를 기준으로 관리자 목록 조회.
     * 키워드, 역할, 상태 조건을 이용한 필터링과 페이지네이션 및 정렬 지원.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param keyword 검색 키워드
     * @param page 요청 페이지 번호
     * @param size 페이지당 조회 개수
     * @param sortBy 정렬 기준 필드
     * @param sortDirection 정렬 방향
     * @param role 관리자 역할 필터 조건
     * @param status 관리자 상태 필터 조건
     * @return 관리자 목록 페이지 응답 DTO
     */
    @GetMapping
    public ResponseEntity<GetAdminsPageResponseDto> getAdmins(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) AdminRole role,
            @RequestParam(required = false) AdminStatus status
    ) {
        int pageIndex = page <= 0 ? 0 : page - 1;

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(direction, sortBy));

        return ResponseEntity.status(HttpStatus.OK)
                .body(adminService.getAdmins(sessionAdmin, keyword, role, status, pageable));
    }

    /**
     * 관리자 단건 조회 API.
     *
     * 경로 변수로 전달받은 관리자 ID 기준 특정 관리자 정보 조회.
     * 로그인한 관리자 권한에 따른 조회 가능 여부 검증은 서비스 계층에서 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param id 조회할 관리자 ID
     * @return 관리자 단건 조회 응답 DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetAdminResponseDto> findById(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminService.findById(sessionAdmin, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateAdminResponseDto> updateAdminInfo(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminInfo(sessionAdmin, id, request));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UpdateAdminRoleResponseDto> updateAdminRole(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminRoleRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminRole(sessionAdmin, id, request));
    }

    @PatchMapping("/{id}/status")
        public ResponseEntity<UpdateAdminStatusResponseDto> updateAdminStatus(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminStatusRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.updateAdminStatus(sessionAdmin, id, request));
    }

}
