package com.msashop.product.adapter.in.web.command.dto;

import jakarta.validation.constraints.NotNull;

public record ChangeStockRequest(@NotNull Integer delta) {}
