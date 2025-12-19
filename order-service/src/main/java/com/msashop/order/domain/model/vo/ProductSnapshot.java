package com.msashop.order.domain.model.vo;

import java.math.BigDecimal;

public record ProductSnapshot(
    long productId,
    String name,
    BigDecimal price,
    int stock
) 
{}
