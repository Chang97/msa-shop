package com.msashop.order.domain.port.out;

import java.util.Optional;

import com.msashop.order.domain.model.vo.ProductSnapshot;

public interface ProductQueryPort {
    Optional<ProductSnapshot> findById(long productId);
}
