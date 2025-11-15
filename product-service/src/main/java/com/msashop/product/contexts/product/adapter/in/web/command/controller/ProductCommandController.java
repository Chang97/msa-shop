package com.msashop.product.contexts.product.adapter.in.web.command.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.msashop.product.contexts.product.adapter.in.web.command.dto.ChangeStockRequest;
import com.msashop.product.contexts.product.adapter.in.web.command.dto.CreateProductRequest;
import com.msashop.product.contexts.product.adapter.in.web.command.dto.CreateProductResponse;
import com.msashop.product.contexts.product.adapter.in.web.command.dto.ProductDetailResponse;
import com.msashop.product.contexts.product.adapter.in.web.command.mapper.ProductCommandWebMapper;
import com.msashop.product.contexts.product.application.command.dto.CreateProductResult;
import com.msashop.product.contexts.product.application.command.port.in.ChangeStockUseCase;
import com.msashop.product.contexts.product.application.command.port.in.CreateProductUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductCommandController {

    private final CreateProductUseCase createProductUseCase;
    private final ChangeStockUseCase changeStockUseCase;
    private final ProductCommandWebMapper mapper;

    @PostMapping
    public ResponseEntity<CreateProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        CreateProductResult result = createProductUseCase.handle(mapper.toCommand(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.productId())
                .toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(result));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDetailResponse> changeStock(@PathVariable Long id,
            @Valid @RequestBody ChangeStockRequest request) {
        return ResponseEntity.ok(
                mapper.toResponse(changeStockUseCase.handle(mapper.toCommand(id, request)))
        );
    }
}
