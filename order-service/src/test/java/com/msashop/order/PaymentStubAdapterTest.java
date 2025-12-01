package com.msashop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.msashop.order.contexts.order.adapter.out.external.payment.PaymentStubAdapter;
import com.msashop.order.contexts.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.contexts.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.contexts.order.application.command.port.in.ChangeOrderStatusUseCase;
import com.msashop.order.contexts.order.domain.model.OrderStatus;
import com.msashop.order.contexts.order.domain.service.StatusTransitionResult;

@ExtendWith(MockitoExtension.class)
class PaymentStubAdapterTest {

    @Mock ChangeOrderStatusUseCase changeOrderStatusUseCase;

    ScheduledExecutorService executor;
    PaymentStubAdapter adapter;

    @BeforeEach
    void setUp() {
        executor = new ScheduledThreadPoolExecutor(1);
        adapter = new PaymentStubAdapter(changeOrderStatusUseCase, executor, 0);
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }

    @Test
    void notifyOrderCreated_marksOrderPaid() throws Exception {
        OrderCreatedEventPayload payload = new OrderCreatedEventPayload(
                10L,
                2L,
                new BigDecimal("12000"),
                "KRW",
                List.of()
        );
        CountDownLatch latch = new CountDownLatch(1);
        when(changeOrderStatusUseCase.handle(any(ChangeOrderStatusCommand.class)))
                .thenAnswer(invocation -> {
                    latch.countDown();
                    return new StatusTransitionResult(OrderStatus.CREATED, OrderStatus.PAID, false);
                });

        adapter.notifyOrderCreated(payload);

        assertThat(latch.await(1, TimeUnit.SECONDS)).isTrue();
        ArgumentCaptor<ChangeOrderStatusCommand> captor = ArgumentCaptor.forClass(ChangeOrderStatusCommand.class);
        verify(changeOrderStatusUseCase).handle(captor.capture());
        assertThat(captor.getValue().orderId()).isEqualTo(10L);
        assertThat(captor.getValue().targetStatus()).isEqualTo(OrderStatus.PAID);
    }
}
