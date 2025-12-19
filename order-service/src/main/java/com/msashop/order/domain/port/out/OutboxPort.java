package com.msashop.order.domain.port.out;

public interface OutboxPort {
    void savePending(String eventType, long aggregateId, Object payload);
}
