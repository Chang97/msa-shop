package com.product_service.contexts.product.adapter.in.web.command.dto;

import jakarta.validation.constraints.NotNull;

public record ChangeStockRequest(@NotNull Integer delta) {}
