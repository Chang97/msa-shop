package com.msashop.order.contexts.order.domain.port.out;

public interface OutboxPort { 
    void savePending(String eventType, long aggregateId, String payloadJson); 
}

