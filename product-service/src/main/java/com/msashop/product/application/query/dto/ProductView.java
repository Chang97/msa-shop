package com.msashop.product.application.query.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductView(
        Long id,
        String name,
        BigDecimal price,
        int stock,
        OffsetDateTime createdAt
) {
}
