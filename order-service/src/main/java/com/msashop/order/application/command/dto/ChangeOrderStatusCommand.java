package com.msashop.order.application.command.dto;

import com.msashop.order.domain.model.OrderStatus;

public record ChangeOrderStatusCommand(
        long orderId,
        OrderStatus targetStatus
) {
}
