package com.msashop.order.contexts.order.adapter.out.external.product;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.msashop.order.contexts.order.domain.model.vo.ProductSnapshot;
import com.msashop.order.contexts.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.contexts.order.domain.port.out.ProductQueryPort;
import com.msashop.order.platform.exception.ConflictException;
import com.msashop.order.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductExternalAdapter implements ProductQueryPort, ProductInventoryPort {
    private final ProductApiClient productApiClient;

    @Override
    public Optional<ProductSnapshot> findById(long productId) {
        try {
            return productApiClient.getProduct(productId)
                    .map(this::toSnapshot);
        } catch (ProductApiClient.ProductApiException ex) {
            throw translateException(productId, ex);
        }
    }

    @Override
    public void reserve(long productId, int qty) {
        validateQty(qty);
        try {
            productApiClient.changeStock(productId, -qty);
        } catch (ProductApiClient.ProductApiException ex) {
            throw translateException(productId, ex);
        }
    }

    @Override
    public void release(long productId, int qty) {
        validateQty(qty);
        try {
            productApiClient.changeStock(productId, qty);
        } catch (ProductApiClient.ProductApiException ex) {
            throw translateException(productId, ex);
        }
    }

    private ProductSnapshot toSnapshot(ProductApiClient.ProductResponse res) {
        return new ProductSnapshot(res.id(), res.name(), res.price(), res.stock() == null ? 0 : res.stock());
    }

    private void validateQty(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다. qty=" + qty);
        }
    }

    private RuntimeException translateException(long productId, ProductApiClient.ProductApiException ex) {
        return switch (ex.errorType()) {
            case NOT_FOUND -> new NotFoundException("상품을 찾을 수 없습니다. productId=" + productId);
            case CONFLICT -> new ConflictException("상품 재고 처리 중 충돌이 발생했습니다. productId=" + productId);
            default -> new IllegalStateException("Product API 호출 중 오류가 발생했습니다.", ex);
        };
    }
}
