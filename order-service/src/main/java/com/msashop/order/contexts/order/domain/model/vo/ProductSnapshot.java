package com.msashop.order.contexts.order.domain.model.vo;

import java.math.BigDecimal;

public record ProductSnapshot(
    long productId,
    String name,
    BigDecimal price,
    int stock
) 
{}
