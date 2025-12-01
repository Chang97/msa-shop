package com.msashop.order.contexts.order.application.query.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.msashop.order.contexts.order.domain.model.OrderStatus;

public record OrderSummaryView(
    Long id,
    String orderNumber,
    OrderStatus status,
    BigDecimal totalAmount,
    Long userId,
    OffsetDateTime createdAt
) {}
