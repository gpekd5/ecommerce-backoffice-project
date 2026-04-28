package com.example.ecommercebackofficeproject.admin.service;

import com.example.ecommercebackofficeproject.admin.dto.request.*;
import com.example.ecommercebackofficeproject.admin.dto.response.*;
import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.global.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 비즈니스 로직 처리 서비스.
 *
 * 관리자 회원가입, 관리자 목록 조회, 관리자 단건 조회 기능 처리.
 * 관리자 조회 기능은 슈퍼 관리자 권한 검증 후 처리.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    /**
     * 관리자 데이터 접근 Repository.
     */
    private final AdminRepository adminRepository;

    /**
     * 비밀번호 암호화 처리 객체.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 관리자 회원가입 처리.
     *
     * 이메일 중복 여부 확인 후 비밀번호 암호화 및 관리자 계정 생성.
     * 신규 관리자 상태는 승인 대기 상태로 설정.
     *
     * @param request 관리자 회원가입 요청 DTO
     * @return 관리자 회원가입 응답 DTO
     */
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
                AdminStatus.PENDING
        );

        Admin saveAdmin = adminRepository.save(admin);

        return SignupAdminResponseDto.from(saveAdmin);
    }

    /**
     * 관리자 목록 조회. (소프트 삭제 데이터 제외)
     *
     * 슈퍼 관리자 권한 검증 후 키워드, 역할, 상태 조건을 기준으로 관리자 목록 조회.
     * 페이지네이션 및 정렬 조건 적용.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param keyword 검색 키워드
     * @param role 관리자 역할 필터 조건
     * @param status 관리자 상태 필터 조건
     * @param pageable 페이지네이션 및 정렬 정보
     * @return 관리자 목록 페이지 응답 DTO
     */
    @Transactional(readOnly = true)
    public GetAdminsPageResponseDto getAdmins(
            SessionAdminDto sessionAdmin,
            String keyword,
            AdminRole role,
            AdminStatus status,
            Pageable pageable
    ) {
        validateSuperAdmin(sessionAdmin);

        Page<GetAdminResponseDto> pageResponse = adminRepository.findAdminsByFilter(keyword, role, status, pageable)
                .map(GetAdminResponseDto::from);

        return new GetAdminsPageResponseDto(pageResponse);
    }

    /**
     * 관리자 단건 조회.(소프트 삭제 데이터 제외)
     *
     * 슈퍼 관리자 권한 검증 후 관리자 ID 기준 관리자 정보 조회.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param adminId 조회할 관리자 ID
     * @return 관리자 단건 조회 응답 DTO
     */
    @Transactional(readOnly = true)
    public GetAdminResponseDto findById(SessionAdminDto sessionAdmin, Long adminId) {

        validateSuperAdmin(sessionAdmin);

        Admin admin = adminRepository.findByIdAndDeletedAtIsNull(adminId).orElseThrow(
                () -> new IllegalStateException("admin whit ID " + adminId + "not found.")
        );

        return GetAdminResponseDto.from(admin);
    }

    /**
     * 관리자 정보 수정.
     *
     * 슈퍼 관리자 권한 검증 후 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 이름, 이메일, 전화번호 정보를 수정.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param adminId 수정할 관리자 ID
     * @param request 관리자 정보 수정 요청 DTO
     * @return 관리자 정보 수정 응답 DTO
     */
    @Transactional
    public UpdateAdminResponseDto updateAdminInfo(SessionAdminDto sessionAdmin, Long adminId, UpdateAdminRequestDto request) {
        validateSuperAdmin(sessionAdmin);

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("admin whit ID " + adminId + "not found.")
        );

        validateEmailDuplicate(request.getEmail(), admin.getEmail());

        admin.updateInfo(request.getName(), request.getEmail(), request.getPhone());

        return UpdateAdminResponseDto.from(admin);
    }

    /**
     * 관리자 역할 수정.
     *
     * 슈퍼 관리자 권한 검증 후 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 요청받은 역할로 관리자 역할 수정.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param adminId 역할을 수정할 관리자 ID
     * @param request 관리자 역할 수정 요청 DTO
     * @return 관리자 역할 수정 응답 DTO
     */
    @Transactional
    public UpdateAdminRoleResponseDto updateAdminRole(SessionAdminDto sessionAdmin, Long adminId, UpdateAdminRoleRequestDto request) {
        validateSuperAdmin(sessionAdmin);

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("admin whit ID " + adminId + "not found.")
        );

        admin.updateRole(request.getRole());

        return UpdateAdminRoleResponseDto.from(admin);
    }

    /**
     * 관리자 상태 수정.
     *
     * 슈퍼 관리자 권한 검증 후 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 요청받은 상태로 관리자 상태 수정.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param adminId 상태를 수정할 관리자 ID
     * @param request 관리자 상태 수정 요청 DTO
     * @return 관리자 상태 수정 응답 DTO
     */
    @Transactional
        public UpdateAdminStatusResponseDto updateAdminStatus(SessionAdminDto sessionAdmin, Long adminId, UpdateAdminStatusRequestDto request) {
        validateSuperAdmin(sessionAdmin);

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("admin whit ID " + adminId + "not found.")
        );

        admin.updateStatus(request.getStatus());

        return UpdateAdminStatusResponseDto.from(admin);
    }

    /**
     * 관리자 승인 처리.
     *
     * 슈퍼 관리자 권한 검증 후 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 승인 대기 상태인 관리자만 승인 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param adminId 승인할 관리자 ID
     * @return 관리자 승인 응답 DTO
     */
    @Transactional
    public UpdateAdminApproveResponseDto approve(SessionAdminDto sessionAdmin, Long adminId) {
        validateSuperAdmin(sessionAdmin);

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("admin whit ID " + adminId + "not found.")
        );

        if (admin.getStatus() != AdminStatus.PENDING) {
            throw new IllegalStateException("승인대기 상태의 관리자만 승인할 수 있습니다.");
        }

        admin.approve();

        return UpdateAdminApproveResponseDto.from(admin);
    }

    /**
     * 관리자 승인 거부 처리.
     *
     * 슈퍼 관리자 권한 검증 후 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 승인 대기 상태인 관리자만 거부 처리.
     * 거부 처리 시 거부 사유 저장.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param adminId 승인 거부할 관리자 ID
     * @param request 관리자 승인 거부 요청 DTO
     * @return 관리자 승인 거부 응답 DTO
     */
    @Transactional
    public UpdateAdminRejectResponseDto reject(SessionAdminDto sessionAdmin, Long adminId, UpdateAdminRejectRequestDto request) {
        validateSuperAdmin(sessionAdmin);

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("admin whit ID " + adminId + "not found.")
        );

        if (admin.getStatus() != AdminStatus.PENDING) {
            throw new IllegalStateException("승인대기 상태의 관리자만 승인할 수 있습니다.");
        }

        admin.reject(request.getRejectReason());

        return UpdateAdminRejectResponseDto.from(admin);
    }

    /**
     * 관리자 삭제 처리.
     *
     * 슈퍼 관리자 권한 검증 후 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 관리자 계정을 소프트 삭제 처리.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param adminId 삭제할 관리자 ID
     */
    @Transactional
    public void delete(SessionAdminDto sessionAdmin, Long adminId) {
        validateSuperAdmin(sessionAdmin);

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("admin whit ID " + adminId + "not found.")
        );

        admin.delete();
    }

    /**
     * 내 프로필 조회.
     *
     * 세션에 저장된 로그인 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 내 프로필 응답 DTO로 변환.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @return 내 프로필 조회 응답 DTO
     */
    @Transactional
    public MeProfileResponseDto getProfile(SessionAdminDto sessionAdmin) {

        Admin admin = adminRepository.findById(sessionAdmin.getAdminId()).orElseThrow(
                () -> new IllegalStateException("Profile ID " + sessionAdmin.getAdminId() + "not found.")
        );

        return MeProfileResponseDto.from(admin);
    }

    /**
     * 내 프로필 수정.
     *
     * 세션에 저장된 로그인 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 이름, 이메일, 전화번호 정보를 수정.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param request 내 프로필 수정 요청 DTO
     * @return 내 프로필 수정 응답 DTO
     */
    @Transactional
    public MeProfileResponseDto updateProfile(SessionAdminDto sessionAdmin, UpdateAdminRequestDto request) {

        Admin admin = adminRepository.findById(sessionAdmin.getAdminId()).orElseThrow(
                () -> new IllegalStateException("Profile ID " + sessionAdmin.getAdminId() + "not found.")
        );

        validateEmailDuplicate(request.getEmail(), admin.getEmail());

        admin.updateInfo(request.getName(), request.getEmail(), request.getPhone());

        return MeProfileResponseDto.from(admin);
    }

    /**
     * 내 비밀번호 변경.
     *
     * 세션에 저장된 로그인 관리자 ID 기준으로 관리자 정보를 조회하고,
     * 현재 비밀번호 일치 여부와 새 비밀번호 확인값 일치 여부 검증 후
     * 새 비밀번호를 암호화하여 저장.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     * @param request 비밀번호 변경 요청 DTO
     */
    @Transactional
    public void updatePassword(SessionAdminDto sessionAdmin, UpdatePasswordRequestDto request) {

        Admin admin = adminRepository.findById(sessionAdmin.getAdminId()).orElseThrow(
                () -> new IllegalStateException("Profile ID " + sessionAdmin.getAdminId() + "not found.")
        );

        if (!passwordEncoder.matches(request.getCurrentPassword(), admin.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!request.getNewPassword().equals(request.getNewPasswordConfirm()))
            throw new IllegalStateException("비밀번호 확인이 일치하지 않습니다.");

        String encodedNewPassword = passwordEncoder.encode(request.getNewPasswordConfirm());

        admin.updatePassword(encodedNewPassword);
    }

    /**
     * 슈퍼 관리자 권한 검증.
     *
     * 세션에 저장된 관리자 역할이 SUPER가 아닌 경우 예외 발생.
     *
     * @param sessionAdmin 세션에 저장된 로그인 관리자 정보
     */
    private void validateSuperAdmin(SessionAdminDto sessionAdmin) {
        if (!AdminRole.SUPER.name().equals(sessionAdmin.getAdminRole())) {
            throw new IllegalStateException("슈퍼 관리자만 접근할 수 있습니다.");
        }
    }


    /**
     * 이메일 중복 여부 검증.
     *
     * 요청 이메일이 존재하고 기존 이메일과 다른 경우에만 중복 여부 확인.
     * 이미 사용 중인 이메일인 경우 예외 발생.
     *
     * @param requestEmail 수정 요청으로 전달된 이메일
     * @param currentEmail 현재 관리자 이메일
     */
    private void validateEmailDuplicate(String requestEmail, String currentEmail) {
        if (requestEmail != null && !requestEmail.equals(currentEmail)) {
            if (adminRepository.existsByEmail(requestEmail)) {
                throw new IllegalStateException("이미 사용중인 이메일입니다.");
            }
        }
    }
}