package com.product_service.contexts.product.adapter.out.persistence.mapper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.product_service.contexts.product.adapter.out.persistence.entity.ProductEntity;
import com.product_service.contexts.product.domain.model.Product;

@Component
public class ProductPersistenceMapper {

    @NonNull
    public ProductEntity toEntity(@NonNull Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setStock(product.getStock());
        entity.setCreatedAt(product.getCreatedAt());
        return entity;
    }

    @Nullable
    public Product toDomain(@Nullable ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return Product.restore(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getStock(),
                entity.getCreatedAt());
    }
}
