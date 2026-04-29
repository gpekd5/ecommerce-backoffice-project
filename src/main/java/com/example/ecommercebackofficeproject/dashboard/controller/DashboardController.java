package com.example.ecommercebackofficeproject.dashboard.controller;


import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.dashboard.dto.DashboardResponseDto;
import com.example.ecommercebackofficeproject.dashboard.dto.DashboardSummaryDto;
import com.example.ecommercebackofficeproject.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 대시보드 전체 데이터 조회 API
     *
     * summary / widgets / charts / recentOrders 데이터를 한 번에 반환합니다.
     *
     * @return 대시보드 통합 응답 데이터
     */
    @GetMapping
    public ResponseEntity<DashboardResponseDto> getDashboard(@RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        DashboardResponseDto result = dashboardService.getDashboard(sessionAdmin.getAdminId());
        return ResponseEntity.ok(result);
    }
}
