package com.msashop.order.contexts.order.domain.model.vo;

import java.math.BigDecimal;

public record Money(BigDecimal value) {
    public Money {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException("money >= 0");
        }
    }
}
