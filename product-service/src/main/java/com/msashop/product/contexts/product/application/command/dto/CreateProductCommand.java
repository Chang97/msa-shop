package com.msashop.product.contexts.product.application.command.dto;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        BigDecimal price,
        Integer stock
) {}
