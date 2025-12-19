package com.msashop.order.domain.port.out;

import com.msashop.order.application.command.dto.OrderCreatedEventPayload;

public interface PaymentPort {

    void notifyOrderCreated(OrderCreatedEventPayload payload);
}
