package com.msashop.order.contexts.order.adapter.out.persistence.event;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderOutboxEntity;
import com.msashop.order.contexts.order.adapter.out.persistence.repo.OrderOutboxRepository;
import com.msashop.order.contexts.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.contexts.order.domain.port.out.PaymentPort;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OutboxPublisher {

    private final OrderOutboxRepository repo;
    private final PaymentPort paymentPort;
    private final ObjectMapper objectMapper;
    private final int batchSize;
    private final boolean enabled;

    public OutboxPublisher(
            OrderOutboxRepository repo,
            PaymentPort paymentPort,
            ObjectMapper objectMapper,
            @Value("${order.outbox.publisher.batch-size:10}") int batchSize,
            @Value("${order.outbox.publisher.enabled:true}") boolean enabled
    ) {
        this.repo = repo;
        this.paymentPort = paymentPort;
        this.objectMapper = objectMapper;
        this.batchSize = batchSize;
        this.enabled = enabled;
    }

    @Scheduled(fixedDelayString = "${order.outbox.publisher.interval-ms:5000}")
    @Transactional
    public void publish() {
        if (!enabled) {
            return;
        }

        List<OrderOutboxEntity> batch = repo.findPendingForUpdate(batchSize);
        if (batch.isEmpty()) {
            return;
        }

        log.debug("Publishing {} outbox events", batch.size());
        for (OrderOutboxEntity entity : batch) {
            try {
                dispatch(entity);
                entity.setStatus("PUBLISHED");
                entity.setPublishedAt(OffsetDateTime.now());
            } catch (Exception ex) {
                log.error("Failed to publish outbox id={} type={}", entity.getId(), entity.getEventType(), ex);
                entity.setStatus("FAILED");
            }
        }
    }

    private void dispatch(OrderOutboxEntity entity) throws IOException {
        if ("ORDER_CREATED".equals(entity.getEventType())) {
            OrderCreatedEventPayload payload = objectMapper.readValue(entity.getPayload(), OrderCreatedEventPayload.class);
            paymentPort.notifyOrderCreated(payload);
        } else {
            log.warn("Unsupported event type {}", entity.getEventType());
        }
    }
}
