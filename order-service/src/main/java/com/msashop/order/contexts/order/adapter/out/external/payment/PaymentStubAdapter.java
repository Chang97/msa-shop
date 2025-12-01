package com.msashop.order.contexts.order.adapter.out.external.payment;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.msashop.order.contexts.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.contexts.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.contexts.order.application.command.port.in.ChangeOrderStatusUseCase;
import com.msashop.order.contexts.order.domain.model.OrderStatus;
import com.msashop.order.contexts.order.domain.port.out.PaymentPort;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaymentStubAdapter implements PaymentPort {

    private final ChangeOrderStatusUseCase changeOrderStatusUseCase;
    private final ScheduledExecutorService executor;
    private final long delayMs;

    public PaymentStubAdapter(
            ChangeOrderStatusUseCase changeOrderStatusUseCase,
            ScheduledExecutorService executor,
            @Value("${order.payment.stub-delay-ms:2000}") long delayMs
    ) {
        this.changeOrderStatusUseCase = changeOrderStatusUseCase;
        this.executor = executor;
        this.delayMs = Math.max(delayMs, 0);
    }

    @Override
    public void notifyOrderCreated(OrderCreatedEventPayload payload) {
        executor.schedule(() -> {
            try {
                changeOrderStatusUseCase.handle(
                        new ChangeOrderStatusCommand(payload.orderId(), OrderStatus.PAID)
                );
                log.info("Payment stub marked order {} as PAID", payload.orderId());
            } catch (Exception ex) {
                log.error("Payment stub failed to update order {}", payload.orderId(), ex);
            }
        }, delayMs, TimeUnit.MILLISECONDS);
    }
}
