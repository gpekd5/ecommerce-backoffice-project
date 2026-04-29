package com.example.ecommercebackofficeproject.dashboard.service;

import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import com.example.ecommercebackofficeproject.dashboard.dto.*;
import com.example.ecommercebackofficeproject.order.repository.OrderRepository;
import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import com.example.ecommercebackofficeproject.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 관리자 대시보드에 필요한 통계 및 데이터들을 조회하는 서비스 클래스입니다.
 *
 * <p>
 * 대시보드는 다음과 같은 영역으로 구성됩니다:
 * <ul>
 *     <li>Summary: 전체 통계 요약 정보</li>
 *     <li>Widgets: 매출 및 상태별 집계 정보</li>
 *     <li>Charts: 차트 시각화를 위한 그룹 통계 데이터</li>
 *     <li>Recent Orders: 최근 주문 목록</li>
 * </ul>
 *
 * <p>
 * 모든 조회는 read-only 트랜잭션으로 수행되며,
 * Repository 계층의 집계 쿼리를 활용하여 효율적으로 데이터를 조회합니다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;


    /**
     * 대시보드에 필요한 모든 데이터를 조회합니다.
     *
     * <p>
     * Summary, Widgets, Charts, Recent Orders 데이터를 각각 조회하여
     * 하나의 응답 DTO로 통합하여 반환합니다.
     *
     * @return 대시보드 전체 데이터를 담은 {@link DashboardResponseDto}
     */
    public DashboardResponseDto getDashboard() {
        return DashboardResponseDto.builder()
                .summary(getSummary())
                .widgets(getWidgets())
                .charts(getCharts())
                .recentOrders(getRecentOrders())
                .build();
    }

    /**
     * 대시보드 Summary 영역의 통계 데이터를 조회합니다.
     *
     * <p>
     * 포함 데이터:
     * <ul>
     *     <li>관리자 수 및 활성 관리자 수</li>
     *     <li>고객 수 및 활성 고객 수</li>
     *     <li>상품 수 및 재고 부족 상품 수</li>
     *     <li>전체 주문 수 및 오늘 주문 수</li>
     *     <li>리뷰 수 및 평균 평점</li>
     * </ul>
     *
     * @return 요약 통계 정보를 담은 {@link DashboardSummaryDto}
     */
    private DashboardSummaryDto getSummary() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();

        Double averageRating = reviewRepository.findAverageRating();
        if (averageRating == null) {
            averageRating = 0.0;
        }

        return DashboardSummaryDto.builder()
                .totalAdminCount(adminRepository.count())
                .activeAdminCount(adminRepository.countByStatus(AdminStatus.ACTIVE))
                .totalCustomerCount(customerRepository.count())
                .activeCustomerCount(customerRepository.countByStatus(CustomerStatus.ACTIVE))
                .totalProductCount(productRepository.count())
                .lowStockProductCount(productRepository.countByStockLessThanEqual(5))
                .totalOrderCount(orderRepository.count())
                .todayOrderCount(orderRepository.countByCreatedAtBetween(startOfToday, startOfTomorrow))
                .totalReviewCount(reviewRepository.count())
                .averageRating(averageRating)
                .build();
    }

    /**
     * 대시보드 Widget 영역의 데이터를 조회합니다.
     *
     * <p>
     * 포함 데이터:
     * <ul>
     *     <li>총 매출 및 오늘 매출</li>
     *     <li>주문 상태별 개수 (준비중, 배송중, 배송완료)</li>
     *     <li>재고 부족 상품 수 및 품절 상품 수</li>
     * </ul>
     *
     * @return 위젯 데이터를 담은 {@link DashboardWidgetsDto}
     */
    private DashboardWidgetsDto getWidgets() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();

        Long totalRevenue = orderRepository.sumTotalRevenue(OrderStatus.CANCELED);
        Long dailyRevenue = orderRepository.sumDailyRevenue(OrderStatus.CANCELED, startOfToday, startOfTomorrow);

        return DashboardWidgetsDto.builder()
                .totalRevenue(totalRevenue != null ? totalRevenue : 0L)
                .dailyRevenue(dailyRevenue != null ? dailyRevenue : 0L)
                .preparingOrderCount(orderRepository.countByOrderStatus(OrderStatus.PREPARING))
                .shippingOrderCount(orderRepository.countByOrderStatus(OrderStatus.SHIPPING))
                .deliveredOrderCount(orderRepository.countByOrderStatus(OrderStatus.DELIVERED))
                .lowStockProductCount(productRepository.countByStockLessThanEqual(5))
                .outOfStockProductCount(productRepository.countByStock(0))
                .build();
    }

    /**
     * 차트 시각화를 위한 데이터를 조회합니다.
     *
     * <p>
     * 포함 데이터:
     * <ul>
     *     <li>리뷰 평점 분포</li>
     *     <li>고객 상태 분포</li>
     *     <li>상품 카테고리 분포</li>
     * </ul>
     *
     * @return 차트 데이터를 담은 {@link DashboardChartsDto}
     */
    private DashboardChartsDto getCharts() {
        return DashboardChartsDto.builder()
                .reviewRating(getReviewRatingChart())
                .customerStatus(getCustomerStatusChart())
                .productCategory(getProductCategoryChart())
                .build();
    }

    /**
     * 최근 주문 데이터를 조회합니다.
     *
     * <p>
     * 생성일 기준 내림차순으로 정렬된 최신 주문 10건을 조회하여
     * 대시보드에 표시하기 위한 DTO로 변환합니다.
     *
     * @return 최근 주문 목록
     */
    private List<ReviewRatingChartDto> getReviewRatingChart() {
        return reviewRepository.countGroupByRating()
                .stream()
                .map(row -> ReviewRatingChartDto.builder()
                        .rating(row.getRating())
                        .count(row.getCount())
                        .build())
                .toList();
    }

    private List<CustomerStatusChartDto> getCustomerStatusChart() {
        return customerRepository.countGroupByStatus()
                .stream()
                .map(row -> CustomerStatusChartDto.builder()
                        .status(row.getStatus())
                        .count(row.getCount())
                        .build())
                .toList();
    }

    private List<ProductCategoryChartDto> getProductCategoryChart() {
        return productRepository.countGroupByCategory()
                .stream()
                .map(row -> ProductCategoryChartDto.builder()
                        .category(row.getCategory())
                        .count(row.getCount())
                        .build())
                .toList();
    }

    /**
     * 최근 주문 데이터를 조회합니다.
     *
     * <p>
     * 생성일 기준 내림차순으로 최신 10건의 주문을 조회하여
     * 대시보드 표시용 DTO로 변환합니다.
     *
     * @return 최근 주문 목록
     */
    private List<DashboardRecentOrderDto> getRecentOrders() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        return orderRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(order -> DashboardRecentOrderDto.builder()
                        .id(order.getId())
                        .orderNumber(order.getOrderNumber())
                        .customerId(order.getCustomer().getId())
                        .customerName(order.getCustomer().getName())
                        .customerEmail(order.getCustomer().getEmail())
                        .productId(order.getProduct().getProductId())
                        .productName(order.getProduct().getProductName())
                        .quantity(order.getQuantity())
                        .amount(Long.valueOf(order.getTotalPrice()))
                        .orderedAt(order.getCreatedAt())
                        .status(order.getOrderStatus())
                        .createdByAdminId(order.getAdmin() != null ? order.getAdmin().getId() : null)
                        .createdByAdminName(order.getAdmin() != null ? order.getAdmin().getName() : null)
                        .createdByAdminEmail(order.getAdmin() != null ? order.getAdmin().getEmail() : null)
                        .createdByAdminRole(order.getAdmin() != null ? order.getAdmin().getRole() : null)
                        .build())
                .toList();
    }
}