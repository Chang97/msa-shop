package com.msashop.product.contexts.product.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.product.contexts.product.adapter.in.web.command.dto.ChangeStockRequest;
import com.msashop.product.contexts.product.adapter.in.web.command.dto.CreateProductRequest;
import com.msashop.product.contexts.product.adapter.in.web.command.dto.CreateProductResponse;
import com.msashop.product.contexts.product.adapter.in.web.command.dto.ProductDetailResponse;
import com.msashop.product.contexts.product.application.command.dto.ChangeStockCommand;
import com.msashop.product.contexts.product.application.command.dto.CreateProductCommand;
import com.msashop.product.contexts.product.application.command.dto.CreateProductResult;
import com.msashop.product.contexts.product.application.command.dto.ProductDetailResult;

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

    public ProductDetailResponse toResponse(ProductDetailResult product) {
        return new ProductDetailResponse(
                product.productId(),
                product.name(),
                product.price(),
                product.stock(),
                product.createdAt()
        );
    }
}
