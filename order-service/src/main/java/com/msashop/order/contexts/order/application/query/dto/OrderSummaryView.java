package com.msashop.order.contexts.order.application.query.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record OrderSummaryView(
    Long id,
    String orderNumber,
    String status,
    BigDecimal totalAmount,
    Long userId,
    OffsetDateTime createdAt
) {}
