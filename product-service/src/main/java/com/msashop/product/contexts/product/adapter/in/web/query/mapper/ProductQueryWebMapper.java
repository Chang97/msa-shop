package com.msashop.product.contexts.product.adapter.in.web.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.msashop.product.contexts.product.adapter.in.web.query.dto.ProductQueryResponse;
import com.msashop.product.contexts.product.application.query.dto.ProductView;

@Component
public class ProductQueryWebMapper {

    public ProductQueryResponse toResponse(ProductView product) {
        return new ProductQueryResponse(
                product.id(),
                product.name(),
                product.price(),
                product.stock(),
                product.createdAt()
        );
    }

    public List<ProductQueryResponse> toResponses(List<ProductView> products) {
        return products.stream()
                .map(this::toResponse)
                .toList();
    }
}
