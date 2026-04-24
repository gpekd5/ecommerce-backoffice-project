package com.example.ecommercebackofficeproject.customer.controller;

import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerDetailResponse;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerPageResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.request.GetCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<GetCustomerPageResponseDto> getCustomers(
            @ModelAttribute GetCustomerRequestDto request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomers(request));
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<GetCustomerDetailResponse> getCustomer(
            @PathVariable Long customerId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomer(customerId));
    }
}
