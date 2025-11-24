package com.msashop.order.contexts.order.application.command.dto;

import com.msashop.order.contexts.order.domain.model.OrderStatus;

public record ChangeOrderStatusCommand(
        long orderId,
        OrderStatus targetStatus
) {
}
