package com.msashop.product.contexts.product.application.command.dto;

public record ChangeStockCommand(Long productId, Integer delta) {}
