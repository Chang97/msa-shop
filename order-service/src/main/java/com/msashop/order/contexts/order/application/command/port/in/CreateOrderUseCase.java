package com.msashop.order.contexts.order.application.command.port.in;

import com.msashop.order.contexts.order.application.command.dto.CreateOrderCommand;

public interface CreateOrderUseCase {
    long handle(CreateOrderCommand cmd);
}
