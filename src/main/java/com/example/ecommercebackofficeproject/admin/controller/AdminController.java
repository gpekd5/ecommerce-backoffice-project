package com.example.ecommercebackofficeproject.admin.controller;

import com.example.ecommercebackofficeproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

}
