package com.msashop.order.application.command.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreatedEventPayload(
        long orderId,
        long userId,
        BigDecimal totalAmount,
        String currency,
        List<OrderItemPayload> items
    ) {
        public record OrderItemPayload(
            long productId,
            String name,
            BigDecimal unitPrice,
            int qty,
            BigDecimal lineAmount
        ) {}
    }
