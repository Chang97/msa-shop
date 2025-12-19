package com.msashop.order.application.command.port.in;

import com.msashop.order.application.command.dto.CreateOrderCommand;

public interface CreateOrderUseCase {
    long handle(CreateOrderCommand cmd);
}
