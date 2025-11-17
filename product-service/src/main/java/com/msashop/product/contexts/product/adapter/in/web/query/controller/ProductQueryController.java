package com.msashop.product.contexts.product.adapter.in.web.query.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msashop.product.contexts.product.adapter.in.web.query.dto.ProductQueryResponse;
import com.msashop.product.contexts.product.adapter.in.web.query.mapper.ProductQueryWebMapper;
import com.msashop.product.contexts.product.application.query.port.in.GetProductsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductQueryController {

    private final GetProductsUseCase getProductsUseCase;
    private final ProductQueryWebMapper mapper;

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @GetMapping
    public List<ProductQueryResponse> list() {
        return mapper.toResponses(getProductsUseCase.handle());
    }
}
