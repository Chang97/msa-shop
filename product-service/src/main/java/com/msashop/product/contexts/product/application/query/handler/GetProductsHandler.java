package com.msashop.product.contexts.product.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.product.contexts.product.application.query.dto.ProductView;
import com.msashop.product.contexts.product.application.query.mapper.ProductQueryMapper;
import com.msashop.product.contexts.product.application.query.port.in.GetProductsUseCase;
import com.msashop.product.contexts.product.domain.port.out.ProductPersistencePort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetProductsHandler implements GetProductsUseCase {

    private final ProductPersistencePort productPersistencePort;
    private final ProductQueryMapper mapper;

    @Override
    public List<ProductView> handle() {
        return mapper.toViews(productPersistencePort.findAll());
    }
}
