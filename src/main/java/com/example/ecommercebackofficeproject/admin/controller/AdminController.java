package com.example.ecommercebackofficeproject.admin.controller;

import com.example.ecommercebackofficeproject.admin.dto.request.*;
import com.example.ecommercebackofficeproject.admin.dto.response.*;
import com.example.ecommercebackofficeproject.admin.service.AdminService;
import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<SignupAdminResponseDto>> signupAdmin(
            @Valid @RequestBody SignupAdminRequestDto request
    ) {


        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        HttpStatus.CREATED,
                        "관리자 회원가입이 완료되었습니다.",
                        adminService.signupAdmin(request)
                ));
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
    public ResponseEntity<ApiResponse<GetAdminsPageResponseDto>> getAdmins(
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

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 목록 조회가 완료되었습니다.",
                        adminService.getAdmins(sessionAdmin, keyword, role, status, pageable)
                        ));
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
    public ResponseEntity<ApiResponse<GetAdminResponseDto>> findById(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 단건 조회가 완료 되었습니다.",
                        adminService.findById(sessionAdmin, id)
                ));
    }

    /**
     * 관리자 정보 수정 API.
     *
     * 경로 변수로 전달받은 관리자 ID 기준 관리자 정보를 수정.
     * 이름, 이메일, 전화번호 정보를 수정하며, 권한 검증은 서비스 계층에서 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param id 수정할 관리자 ID
     * @param request 관리자 정보 수정 요청 DTO
     * @return 관리자 정보 수정 응답 DTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateAdminResponseDto>> updateAdminInfo(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 정보 수정이 완료되었습니다.",
                        adminService.updateAdminInfo(sessionAdmin, id, request)
                ));
    }

    /**
     * 관리자 역할 수정 API.
     *
     * 경로 변수로 전달받은 관리자 ID 기준 관리자 역할을 수정.
     * 권한 검증은 서비스 계층에서 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param id 역할을 수정할 관리자 ID
     * @param request 관리자 역할 수정 요청 DTO
     * @return 관리자 역할 수정 응답 DTO
     */
    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UpdateAdminRoleResponseDto>> updateAdminRole(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminRoleRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 역할 수정이 완료되었습니다.",
                        adminService.updateAdminRole(sessionAdmin, id, request)
                ));
    }

    /**
     * 관리자 상태 수정 API.
     *
     * 경로 변수로 전달받은 관리자 ID 기준 관리자 상태를 수정.
     * 권한 검증은 서비스 계층에서 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param id 상태를 수정할 관리자 ID
     * @param request 관리자 상태 수정 요청 DTO
     * @return 관리자 상태 수정 응답 DTO
     */
    @PatchMapping("/{id}/status")
        public ResponseEntity<ApiResponse<UpdateAdminStatusResponseDto>> updateAdminStatus(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminStatusRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 상태 수정이 완료되었습니다.",
                        adminService.updateAdminStatus(sessionAdmin, id, request)
                ));
    }

    /**
     * 관리자 승인 API.
     *
     * 경로 변수로 전달받은 관리자 ID 기준 승인 대기 상태의 관리자를 승인 처리.
     * 권한 검증 및 승인 가능 상태 검증은 서비스 계층에서 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param id 승인할 관리자 ID
     * @return 관리자 승인 응답 DTO
     */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<UpdateAdminApproveResponseDto>> approve(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 승인이 완료되었습니다.",
                        adminService.approve(sessionAdmin, id)
                ));
    }

    /**
     * 관리자 승인 거부 API.
     *
     * 경로 변수로 전달받은 관리자 ID 기준 승인 대기 상태의 관리자를 거부 처리.
     * 거부 사유는 요청 본문으로 전달받으며, 권한 검증 및 거부 가능 상태 검증은 서비스 계층에서 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param id 승인 거부할 관리자 ID
     * @param request 관리자 승인 거부 요청 DTO
     * @return 관리자 승인 거부 응답 DTO
     */
    @PatchMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<UpdateAdminRejectResponseDto>> reject(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminRejectRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 승인 거부가 완료되었습니다.",
                        adminService.reject(sessionAdmin, id, request)
                ));
    }

    /**
     * 관리자 삭제 API.
     *
     * 경로 변수로 전달받은 관리자 ID 기준 관리자 계정을 소프트 삭제 처리.
     * 권한 검증은 서비스 계층에서 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param id 삭제할 관리자 ID
     * @return 응답 본문 없음
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @PathVariable Long id
    ) {
        adminService.delete(sessionAdmin, id);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "관리자 삭제가 완료되었습니다.",
                        null
                ));
    }

    /**
     * 내 프로필 조회 API.
     *
     * 세션에 저장된 로그인 관리자 정보를 기준으로 내 프로필 정보 조회.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @return 내 프로필 조회 응답 DTO
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MeProfileResponseDto>> getProfile(@SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "내 프로필 조회가 되었습니다.",
                        adminService.getProfile(sessionAdmin)
                ));
    }

    /**
     * 내 프로필 수정 API.
     *
     * 세션에 저장된 로그인 관리자 정보를 기준으로 내 프로필 정보를 수정.
     * 이름, 이메일, 전화번호 정보를 수정.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param request 내 프로필 수정 요청 DTO
     * @return 내 프로필 수정 응답 DTO
     */
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<MeProfileResponseDto>> updateProfile(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdmin,
            @Valid @RequestBody UpdateAdminRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "내 프로필 정보가 수정되었습니다.",
                        adminService.updateProfile(sessionAdmin, request)
                ));
    }

    /**
     * 내 비밀번호 변경 API.
     *
     * 세션에 저장된 로그인 관리자 정보를 기준으로 비밀번호를 변경.
     * 현재 비밀번호 검증, 새 비밀번호 확인값 검증, 새 비밀번호 암호화 저장은 서비스 계층에서 처리.
     *
     * @param sessionAdminDto 세션에 저장된 로그인 관리자 정보
     * @param request 비밀번호 변경 요청 DTO
     * @return 응답 본문 없음
     */
    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @SessionAttribute(name = "loginUser") SessionAdminDto sessionAdminDto,
            @Valid @RequestBody UpdatePasswordRequestDto request
    ) {
        adminService.updatePassword(sessionAdminDto, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "내 비밀번호 변경이 되었습니다.",
                        null
                ));
    }

}
