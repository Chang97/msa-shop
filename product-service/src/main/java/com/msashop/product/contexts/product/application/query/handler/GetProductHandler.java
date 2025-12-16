package com.msashop.product.contexts.product.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.product.contexts.product.application.query.dto.ProductView;
import com.msashop.product.contexts.product.application.query.mapper.ProductQueryMapper;
import com.msashop.product.contexts.product.application.query.port.in.GetProductUseCase;
import com.msashop.product.contexts.product.domain.model.exception.ProductNotFoundException;
import com.msashop.product.contexts.product.domain.port.out.ProductPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetProductHandler implements GetProductUseCase {

    private final ProductPersistencePort productPersistencePort;
    private final ProductQueryMapper mapper;

    @Override
    public ProductView handle(long id) {
        return productPersistencePort
                .findById(id)
                .map(mapper::toView)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
