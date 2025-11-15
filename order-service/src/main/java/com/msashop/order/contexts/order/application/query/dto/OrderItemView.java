package com.msashop.order.contexts.order.application.query.dto;

import java.math.BigDecimal;

public record OrderItemView (
    Long id, 
    Long productId, 
    String name, 
    BigDecimal unitPrice, 
    int qty, 
    BigDecimal lineAmount
) {}
