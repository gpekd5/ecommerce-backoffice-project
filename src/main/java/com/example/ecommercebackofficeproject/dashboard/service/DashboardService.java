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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public DashboardResponseDto getDashboard() {
        return DashboardResponseDto.builder()
                .summary(getSummary())
                .widgets(getWidgets())
                .charts(getCharts())
                .recentOrders(getRecentOrders())
                .build();
    }

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

    private DashboardChartsDto getCharts() {
        return DashboardChartsDto.builder()
                .reviewRating(getReviewRatingChart())
                .customerStatus(getCustomerStatusChart())
                .productCategory(getProductCategoryChart())
                .build();
    }

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