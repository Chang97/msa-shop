package com.msashop.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderOutboxEntity;
import com.msashop.order.contexts.order.adapter.out.persistence.event.OutboxPublisher;
import com.msashop.order.contexts.order.adapter.out.persistence.repo.OrderOutboxRepository;
import com.msashop.order.contexts.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.contexts.order.domain.port.out.PaymentPort;

@ExtendWith(MockitoExtension.class)
class OutboxPublisherTest {

    @Mock OrderOutboxRepository orderOutboxRepository;
    @Mock PaymentPort paymentPort;

    ObjectMapper objectMapper;
    OutboxPublisher outboxPublisher;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        outboxPublisher = new OutboxPublisher(orderOutboxRepository, paymentPort, objectMapper, 10, true);
    }

    @Test
    void publish_dispatchesEvents() throws Exception {
        OrderCreatedEventPayload payload = new OrderCreatedEventPayload(
                1L,
                5L,
                new BigDecimal("1000"),
                "KRW",
                List.of()
        );
        OrderOutboxEntity entity = new OrderOutboxEntity(1L, "ORDER_CREATED", toJson(payload));
        entity.setId(100L);

        when(orderOutboxRepository.findPendingForUpdate(10)).thenReturn(List.of(entity));

        outboxPublisher.publish();

        verify(paymentPort).notifyOrderCreated(payload);
        assertThat(entity.getStatus()).isEqualTo("PUBLISHED");
        assertThat(entity.getPublishedAt()).isNotNull();
    }

    @Test
    void publish_noPending_doesNothing() {
        when(orderOutboxRepository.findPendingForUpdate(10)).thenReturn(List.of());

        outboxPublisher.publish();

        verifyNoInteractions(paymentPort);
    }

    private String toJson(OrderCreatedEventPayload payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
    }
}
