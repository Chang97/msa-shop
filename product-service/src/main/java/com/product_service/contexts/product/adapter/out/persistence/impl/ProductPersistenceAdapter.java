package com.product_service.contexts.product.adapter.out.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.product_service.contexts.product.adapter.out.persistence.mapper.ProductPersistenceMapper;
import com.product_service.contexts.product.adapter.out.persistence.repo.ProductRepository;
import com.product_service.contexts.product.domain.model.Product;
import com.product_service.contexts.product.domain.port.out.ProductPersistencePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;
    private final ProductPersistenceMapper mapper;

    @Override
    public Product save(Product product) {
        return mapper.toDomain(productRepository.save(mapper.toEntity(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
