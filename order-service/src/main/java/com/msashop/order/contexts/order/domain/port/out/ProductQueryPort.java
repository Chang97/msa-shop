package com.msashop.order.contexts.order.domain.port.out;

import java.util.Optional;

import com.msashop.order.contexts.order.domain.model.vo.ProductSnapshot;

public interface ProductQueryPort {
    Optional<ProductSnapshot> findById(long productId);
}
