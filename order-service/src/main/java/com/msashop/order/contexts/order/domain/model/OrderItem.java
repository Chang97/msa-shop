package com.msashop.order.contexts.order.domain.model;

import com.msashop.order.contexts.order.domain.model.vo.Money;

public record OrderItem (
    long productId,
    String productName,
    Money unitPrice,
    int qty
) {
    public Money lineAmount() { 
        return new Money(unitPrice.value().multiply(java.math.BigDecimal.valueOf(qty))); 
    }
}
