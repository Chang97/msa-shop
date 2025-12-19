package com.msashop.order.application.command.dto;

public record ReservedItem(
    long productId,
    int qty
) {}
