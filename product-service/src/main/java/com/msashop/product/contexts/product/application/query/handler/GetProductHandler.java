package com.msashop.product.contexts.product.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.product.contexts.product.application.query.port.in.GetProductUseCase;
import com.msashop.product.contexts.product.domain.model.Product;
import com.msashop.product.contexts.product.domain.port.out.ProductPersistencePort;
import com.msashop.product.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetProductHandler implements GetProductUseCase {

    private final ProductPersistencePort productPersistencePort;

    @Override
    public Product handle(long id) {
        Product product = productPersistencePort
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product Not Found."));
        return product;
    }
}
