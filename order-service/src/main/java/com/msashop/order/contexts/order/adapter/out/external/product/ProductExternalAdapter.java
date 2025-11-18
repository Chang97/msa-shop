package com.msashop.order.contexts.order.adapter.out.external.product;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.msashop.order.contexts.order.domain.model.vo.ProductSnapshot;
import com.msashop.order.contexts.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.contexts.order.domain.port.out.ProductQueryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductExternalAdapter implements ProductQueryPort, ProductInventoryPort {
    private final ProductApiClient productApiClient;

    @Override
    public Optional<ProductSnapshot> findById(long productId) {
        throw new UnsupportedOperationException("TODO: call product service");
    }

    @Override
    public void reserve(long productId, int qty) {
        throw new UnsupportedOperationException("TODO: reserve stock via product servivce");
        
    }

    @Override
    public void release(long productId, int qty) {
        throw new UnsupportedOperationException("TODO: release stock via product service");
        
    }
}
