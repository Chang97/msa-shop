package com.msashop.product.adapter.in.web.query.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductQueryResponse(
        Long id,
        String name,
        BigDecimal price,
        Integer stock,
        OffsetDateTime createdAt
) {}
