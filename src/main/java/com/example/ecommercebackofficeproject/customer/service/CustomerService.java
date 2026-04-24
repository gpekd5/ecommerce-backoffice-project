package com.example.ecommercebackofficeproject.customer.service;

import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerPageResponse;
import com.example.ecommercebackofficeproject.customer.dto.request.GetCustomerRequest;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerResponse;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.customer.type.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public GetCustomerPageResponse getCustomers(GetCustomerRequest request) {
        CustomerStatus status = parseStatus(request.getStatus());

        Sort sort = createSort(request.getSortBy(), request.getSortDir());

        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                sort
        );

        Page<GetCustomerResponse> customerPage = customerRepository.searchCustomers(
                request.getKeyword(),
                status,
                pageable
        );

        return GetCustomerPageResponse.from(customerPage);
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

    private Sort createSort(String sortBy, String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return Sort.by(direction, sortBy);
    }
}
