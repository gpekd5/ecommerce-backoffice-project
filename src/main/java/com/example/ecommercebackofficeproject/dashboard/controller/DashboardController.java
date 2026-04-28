package com.example.ecommercebackofficeproject.dashboard.controller;


import com.example.ecommercebackofficeproject.dashboard.dto.Response.DashboardSummaryResponseDto;
import com.example.ecommercebackofficeproject.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponseDto> getSummary() {
        DashboardSummaryResponseDto result = dashboardService.getSummary();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
