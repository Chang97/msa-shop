package com.msashop.order.contexts.order.adapter.out.persistence.impl;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderOutboxEntity;
import com.msashop.order.contexts.order.adapter.out.persistence.repo.OrderOutboxRepository;
import com.msashop.order.contexts.order.domain.port.out.OutboxPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OutboxAdapter implements OutboxPort {

    private final OrderOutboxRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public void savePending(String eventType, long aggregateId, Object payloadJson) {
        String payload = toJson(payloadJson);
        OrderOutboxEntity entity = new OrderOutboxEntity(aggregateId, eventType, payload);
        repository.save(entity);
    }

    private String toJson(Object payloadJson) {
        try {
            return objectMapper.writeValueAsString(payloadJson);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid outbox payload", e);
        }
    }
}
