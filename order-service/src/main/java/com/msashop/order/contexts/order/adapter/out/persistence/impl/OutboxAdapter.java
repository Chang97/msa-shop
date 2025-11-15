package com.msashop.order.contexts.order.adapter.out.persistence.impl;

import org.springframework.stereotype.Repository;

import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderOutboxEntity;
import com.msashop.order.contexts.order.adapter.out.persistence.repo.OrderOutboxRepository;
import com.msashop.order.contexts.order.domain.port.out.OutboxPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OutboxAdapter implements OutboxPort {

    private final OrderOutboxRepository repository;

    @Override
    public void savePending(String eventType, long aggregateId, String payloadJson) {
        OrderOutboxEntity entity = new OrderOutboxEntity(aggregateId, eventType, payloadJson);
        repository.save(entity);
    }
}
