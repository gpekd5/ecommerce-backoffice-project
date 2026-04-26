package com.example.ecommercebackofficeproject.customer.service;

import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerStatusRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.response.*;
import com.example.ecommercebackofficeproject.customer.dto.request.GetCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /*고객 리스트 조회*/
    @Transactional(readOnly = true)
    public GetCustomerPageResponseDto getCustomers(GetCustomerRequestDto request) {
        CustomerStatus status = parseStatus(request.getStatus());

        Pageable pageable = createPageable(
                request.getPage(),
                request.getSize(),
                request.getSortBy(),
                request.getSortDir()
        );

        Specification<Customer> specification = createSearchSpecification(
                request.getKeyword(),
                status
        );

        Page<Customer> customerPage = customerRepository.findAll(specification, pageable);

        List<GetCustomerResponseDto> items = customerPage.getContent()
                .stream()
                .map(customer -> {
                    Long totalOrderCount = customerRepository.countOrdersByCustomerId(customer.getId());
                    Long totalOrderAmount = customerRepository.sumTotalOrderAmountByCustomerId(customer.getId());

                    return GetCustomerResponseDto.from(
                            customer,
                            totalOrderCount,
                            totalOrderAmount
                    );
                })
                .toList();

        return GetCustomerPageResponseDto.from(customerPage, items);
    }

    private Specification<Customer> createSearchSpecification(
            String keyword,
            CustomerStatus status
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

            if (keyword != null && !keyword.isBlank()) {
                Predicate nameLike = criteriaBuilder.like(
                        root.get("name"),
                        "%" + keyword + "%"
                );

                Predicate emailLike = criteriaBuilder.like(
                        root.get("email"),
                        "%" + keyword + "%"
                );

                predicates.add(criteriaBuilder.or(nameLike, emailLike));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private CustomerStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        try {
            return CustomerStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 고객 상태입니다.");
        }
    }

    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        if (page < 1) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }

        if (size < 1) {
            throw new IllegalArgumentException("페이지 크기는 1 이상이어야 합니다.");
        }

        String validatedSortBy = validateSortBy(sortBy);

        Sort.Direction direction = sortDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return PageRequest.of(
                page - 1,
                size,
                Sort.by(direction, validatedSortBy)
        );
    }

    private String validateSortBy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "createdAt";
        }

        return switch (sortBy) {
            case "name", "email", "createdAt" -> sortBy;
            default -> throw new IllegalArgumentException("유효하지 않은 정렬 기준입니다.");
        };
    }

    /*고객 상세 조회*/
    @Transactional(readOnly = true)
    public GetCustomerDetailResponseDto getCustomer(Long customerId) {

        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        Long totalOrderCount = customerRepository.countOrdersByCustomerId(customer.getId());
        Long totalOrderAmount = customerRepository.sumTotalOrderAmountByCustomerId(customer.getId());

        return GetCustomerDetailResponseDto.from(
                customer,
                totalOrderCount,
                totalOrderAmount
        );
    }

    @Transactional
    public UpdateCustomerResponseDto updateCustomer(
            Long customerId,
            UpdateCustomerRequestDto request
    ) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        if (customerRepository.existsByEmailAndIdNot(request.getEmail(), customerId)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (customerRepository.existsByPhoneAndIdNot(request.getPhone(), customerId)) {
            throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
        }

        customer.updateInfo(
                request.getName(),
                request.getEmail(),
                request.getPhone()
        );

        return UpdateCustomerResponseDto.from(customer);
    }

    @Transactional
    public UpdateCustomerStatusResponseDto updateCustomerStatus(
            Long customerId,
            UpdateCustomerStatusRequestDto request
    ) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        CustomerStatus newStatus = parseStatus(request.getStatus());

        if (newStatus == null) {
            throw new IllegalArgumentException("유효하지 않은 상태입니다.");
        }

        customer.changeStatus(newStatus);

        return UpdateCustomerStatusResponseDto.from(customer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findByIdAndDeletedAtIsNull(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        customer.delete();
    }
}
