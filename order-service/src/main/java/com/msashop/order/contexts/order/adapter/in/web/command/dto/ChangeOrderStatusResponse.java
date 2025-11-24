package com.msashop.order.contexts.order.adapter.in.web.command.dto;

public record ChangeOrderStatusResponse(
        String from,
        String to,
        boolean inventoryReleased
) { }
