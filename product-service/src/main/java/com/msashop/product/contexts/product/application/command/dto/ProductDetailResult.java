package com.msashop.product.contexts.product.application.command.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductDetailResult(
        Long productId,
        String name,
        BigDecimal price,
        int stock,
        OffsetDateTime createdAt
) {
}
