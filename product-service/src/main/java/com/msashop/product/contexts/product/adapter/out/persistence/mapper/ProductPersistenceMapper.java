package com.msashop.product.contexts.product.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.msashop.product.contexts.product.adapter.out.persistence.entity.ProductEntity;
import com.msashop.product.contexts.product.domain.model.Product;

@Component
public class ProductPersistenceMapper {

    public ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setStock(product.getStock());
        entity.setCreatedAt(product.getCreatedAt());
        return entity;
    }

    public Product toDomain(ProductEntity entity) {
        return Product.restore(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock(),
                entity.getCreatedAt());
    }
}
