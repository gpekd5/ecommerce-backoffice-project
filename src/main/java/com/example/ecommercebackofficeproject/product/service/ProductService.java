package com.example.ecommercebackofficeproject.product.service;

import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


}
