package com.product_service.contexts.product.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.product_service.contexts.product.domain.model.Product;

public interface ProductPersistencePort {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();
}
