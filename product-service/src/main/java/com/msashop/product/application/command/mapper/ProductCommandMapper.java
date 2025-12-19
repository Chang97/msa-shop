package com.msashop.product.application.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.product.application.command.dto.ProductDetailResult;
import com.msashop.product.domain.model.Product;

@Component
public class ProductCommandMapper {

    public ProductDetailResult toDetailResult(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDetailResult(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt()
        );
    }
}
