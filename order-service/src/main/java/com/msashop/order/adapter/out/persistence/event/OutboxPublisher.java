package com.msashop.order.adapter.out.persistence.event;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msashop.order.adapter.out.persistence.entity.OrderOutboxEntity;
import com.msashop.order.adapter.out.persistence.repo.OrderOutboxRepository;
import com.msashop.order.application.command.dto.OrderCreatedEventPayload;
import com.msashop.order.domain.port.out.PaymentPort;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OutboxPublisher {

    private final OrderOutboxRepository repo;
    private final PaymentPort paymentPort;
    private final ObjectMapper objectMapper;
    private final MeterRegistry meterRegistry;
    private final int batchSize;
    private final boolean enabled;
    private final Counter publishedCounter;
    private final Counter failedCounter;
    private final Timer publishTimer;
    private final AtomicLong pendingGauge;

    public OutboxPublisher(
            OrderOutboxRepository repo,
            PaymentPort paymentPort,
            ObjectMapper objectMapper,
            MeterRegistry meterRegistry,
            @Value("${order.outbox.publisher.batch-size:10}") int batchSize,
            @Value("${order.outbox.publisher.enabled:true}") boolean enabled
    ) {
        this.repo = repo;
        this.paymentPort = paymentPort;
        this.objectMapper = objectMapper;
        this.meterRegistry = meterRegistry;
        this.batchSize = batchSize;
        this.enabled = enabled;
        this.publishedCounter = Counter.builder("order_outbox_published_total")
                .description("Number of successfully published order outbox events")
                .tag("event", "ORDER_CREATED")
                .register(meterRegistry);
        this.failedCounter = Counter.builder("order_outbox_failed_total")
                .description("Number of order outbox events that failed to publish")
                .register(meterRegistry);
        this.publishTimer = Timer.builder("order_outbox_publish_latency")
                .description("Outbox publishing execution time")
                .register(meterRegistry);
        this.pendingGauge = meterRegistry.gauge("order_outbox_pending", new AtomicLong(0));
    }

    @Scheduled(fixedDelayString = "${order.outbox.publisher.interval-ms:5000}")
    @Transactional
    public void publish() {
        if (!enabled) {
            return;
        }

        List<OrderOutboxEntity> batch = repo.findPendingForUpdate(batchSize);
        if (batch.isEmpty()) {
            updatePendingGauge();
            return;
        }

        log.debug("Publishing {} outbox events", batch.size());
        Timer.Sample sample = Timer.start(meterRegistry);
        for (OrderOutboxEntity entity : batch) {
            try {
                dispatch(entity);
                entity.setStatus("PUBLISHED");
                entity.setPublishedAt(OffsetDateTime.now());
                publishedCounter.increment();
            } catch (Exception ex) {
                log.error("Failed to publish outbox id={} type={}", entity.getId(), entity.getEventType(), ex);
                entity.setStatus("FAILED");
                failedCounter.increment();
            }
        }
        sample.stop(publishTimer);
        updatePendingGauge();
    }

    private void dispatch(OrderOutboxEntity entity) throws IOException {
        if ("ORDER_CREATED".equals(entity.getEventType())) {
            OrderCreatedEventPayload payload = objectMapper.readValue(entity.getPayload(), OrderCreatedEventPayload.class);
            paymentPort.notifyOrderCreated(payload);
        } else {
            log.warn("Unsupported event type {}", entity.getEventType());
        }
    }

    private void updatePendingGauge() {
        if (pendingGauge != null) {
            pendingGauge.set(repo.countByStatus("PENDING"));
        }
    }
}
