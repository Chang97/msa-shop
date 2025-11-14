package com.product_service.contexts.product.application.command.dto;

public record ChangeStockCommand(Long productId, Integer delta) {}
