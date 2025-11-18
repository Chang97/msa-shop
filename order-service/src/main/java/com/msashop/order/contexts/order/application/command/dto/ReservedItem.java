package com.msashop.order.contexts.order.application.command.dto;

public record ReservedItem(
    long productId,
    int qty
) {}
