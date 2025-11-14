package com.product_service.contexts.product.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.product_service.contexts.product.adapter.in.web.query.dto.ProductQueryResponse;
import com.product_service.contexts.product.domain.model.Product;

@Component
public class ProductQueryWebMapper {

    public ProductQueryResponse toResponse(Product product) {
        return new ProductQueryResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt()
        );
    }

    public List<ProductQueryResponse> toResponses(List<Product> products) {
        return products.stream()
                .map(this::toResponse)
                .toList();
    }
}
