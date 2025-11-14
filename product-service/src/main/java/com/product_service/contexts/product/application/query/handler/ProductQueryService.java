package com.product_service.contexts.product.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product_service.contexts.product.application.query.port.in.GetProductsUseCase;
import com.product_service.contexts.product.domain.model.Product;
import com.product_service.contexts.product.domain.port.out.ProductPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService implements GetProductsUseCase {

    private final ProductPersistencePort productPersistencePort;

    @Override
    public List<Product> handle() {
        return productPersistencePort.findAll();
    }
}
