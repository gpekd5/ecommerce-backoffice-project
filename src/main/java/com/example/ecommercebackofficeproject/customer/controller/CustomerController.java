package com.example.ecommercebackofficeproject.customer.controller;

import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerPageResponse;
import com.example.ecommercebackofficeproject.customer.dto.request.GetCustomerRequest;
import com.example.ecommercebackofficeproject.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<GetCustomerPageResponse> getCustomers(
            @ModelAttribute GetCustomerRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomers(request));
    }
}
