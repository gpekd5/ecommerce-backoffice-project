package com.example.ecommercebackofficeproject.customer.controller;

import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerStatusRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerDetailResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerPageResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.request.GetCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.response.UpdateCustomerResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.response.UpdateCustomerStatusResponseDto;
import com.example.ecommercebackofficeproject.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<GetCustomerDetailResponseDto> getCustomer(
            @PathVariable Long customerId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomer(customerId));
    }

    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<UpdateCustomerResponseDto> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerRequestDto request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.updateCustomer(customerId, request));
    }

    @PatchMapping("/customers/{customerId}/status")
    public ResponseEntity<UpdateCustomerStatusResponseDto> updateCustomerStatus(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerStatusRequestDto request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.updateCustomerStatus(customerId, request));
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long customerId
    ) {
        customerService.deleteCustomer(customerId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
