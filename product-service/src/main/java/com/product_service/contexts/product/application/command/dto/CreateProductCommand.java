package com.product_service.contexts.product.application.command.dto;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        BigDecimal price,
        Integer stock
) {}
