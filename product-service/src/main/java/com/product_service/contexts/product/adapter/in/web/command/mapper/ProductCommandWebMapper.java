package com.product_service.contexts.product.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.product_service.contexts.product.adapter.in.web.command.dto.ChangeStockRequest;
import com.product_service.contexts.product.adapter.in.web.command.dto.CreateProductRequest;
import com.product_service.contexts.product.adapter.in.web.command.dto.CreateProductResponse;
import com.product_service.contexts.product.adapter.in.web.command.dto.ProductDetailResponse;
import com.product_service.contexts.product.application.command.dto.ChangeStockCommand;
import com.product_service.contexts.product.application.command.dto.CreateProductCommand;
import com.product_service.contexts.product.application.command.dto.CreateProductResult;
import com.product_service.contexts.product.domain.model.Product;

@Component
public class ProductCommandWebMapper {

    public CreateProductCommand toCommand(CreateProductRequest request) {
        return new CreateProductCommand(request.name(), request.price(), request.stock());
    }

    public ChangeStockCommand toCommand(Long id, ChangeStockRequest request) {
        return new ChangeStockCommand(id, request.delta());
    }

    public CreateProductResponse toResponse(CreateProductResult result) {
        return new CreateProductResponse(result.productId());
    }

    public ProductDetailResponse toResponse(Product product) {
        return new ProductDetailResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt()
        );
    }
}
