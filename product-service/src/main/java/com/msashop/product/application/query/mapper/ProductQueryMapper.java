package com.msashop.product.application.query.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.msashop.product.application.query.dto.ProductView;
import com.msashop.product.domain.model.Product;

@Component
public class ProductQueryMapper {

    public ProductView toView(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductView(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt()
        );
    }

    public List<ProductView> toViews(List<Product> products) {
        return products.stream()
                .map(this::toView)
                .toList();
    }
}
