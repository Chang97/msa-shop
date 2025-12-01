package com.msashop.order.contexts.order.domain.port.out;

import com.msashop.order.contexts.order.application.command.dto.OrderCreatedEventPayload;

public interface PaymentPort {

    void notifyOrderCreated(OrderCreatedEventPayload payload);
}
