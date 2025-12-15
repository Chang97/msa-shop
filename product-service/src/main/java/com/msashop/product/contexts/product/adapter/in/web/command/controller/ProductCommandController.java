package com.msashop.product.contexts.product.adapter.in.web.command.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.msashop.product.platform.exception.UnauthorizedException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductCommandController {

    private final CreateProductUseCase createProductUseCase;
    private final ChangeStockUseCase changeStockUseCase;
    private final ProductCommandWebMapper mapper;

    @PostMapping
    public ResponseEntity<CreateProductResponse> create(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @Valid @RequestBody CreateProductRequest request) {
        ensureAuthenticated(userId);
        CreateProductResult result = createProductUseCase.handle(mapper.toCommand(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.productId())
                .toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(result));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDetailResponse> changeStock(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @PathVariable("id") Long id,
            @Valid @RequestBody ChangeStockRequest request) {
        ensureAuthenticated(userId);
        return ResponseEntity.ok(
                mapper.toResponse(changeStockUseCase.handle(mapper.toCommand(id, request)))
        );
    }

    private void ensureAuthenticated(Long userId) {
        if (userId == null) {
            throw new UnauthorizedException("요청에 인증 정보가 없습니다.");
        }
    }
}
