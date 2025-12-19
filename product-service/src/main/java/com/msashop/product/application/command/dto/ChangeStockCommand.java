package com.msashop.product.application.command.dto;

public record ChangeStockCommand(Long productId, Integer delta) {}
