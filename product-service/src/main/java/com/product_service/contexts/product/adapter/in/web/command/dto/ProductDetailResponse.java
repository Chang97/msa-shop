package com.product_service.contexts.product.adapter.in.web.command.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductDetailResponse(
        Long id,
        String name,
        BigDecimal price,
        Integer stock,
        OffsetDateTime createdAt
) {}
