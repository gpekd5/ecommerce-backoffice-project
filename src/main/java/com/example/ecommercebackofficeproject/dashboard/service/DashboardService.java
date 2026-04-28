package com.example.ecommercebackofficeproject.dashboard.service;

import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import com.example.ecommercebackofficeproject.dashboard.dto.Response.DashboardSummaryResponseDto;
import com.example.ecommercebackofficeproject.order.repository.OrderRepository;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import com.example.ecommercebackofficeproject.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 대시보드 요약 통계 조회
     *
     * 조회 항목:
     * - 관리자 수 (전체 / 활성)
     * - 고객 수 (전체 / 활성)
     * - 상품 수 (전체 / 재고 부족)
     * - 주문 수 (전체 / 오늘 주문)
     * - 리뷰 수 (전체 / 평균 평점)
     *
     * 특징:
     * - 오늘 주문 수는 당일 기준으로 계산
     * - 평균 평점이 없으면 0.0 반환
     *
     * @return 대시보드 요약 정보
     */
//    @Transactional(readOnly = true)
//    public DashboardSummaryResponseDto getSummary() {
//
//        // 날짜 설정 (오늘의 시작 ~ 내일의 시작 전까지)
//        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
//        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();
//
//        Double averageRating = reviewRepository.findAverageRating();
//        if (averageRating == null) {
//            averageRating = 0.0;
//        }
//
//        return DashboardSummaryResponseDto.builder()
//                .totalAdminCount(adminRepository.count())
//                .activeAdminCount(adminRepository.countByStatus(AdminStatus.ACTIVE))
//
//                .totalCustomerCount(customerRepository.count())
//                .activeCustomerCount(customerRepository.countByStatus(CustomerStatus.ACTIVE))
//
//                .totalProductCount(productRepository.count())
//                .lowStockProductCount(productRepository.countByStockLessThanEqual(5))
//
//                .totalOrderCount(orderRepository.count())
//                .todayOrderCount(orderRepository.countByCreatedAtBetween(startOfToday, startOfTomorrow))
//
//                .totalReviewCount(reviewRepository.count())
//                .averageRating(averageRating)
//                .build();
//    }
}