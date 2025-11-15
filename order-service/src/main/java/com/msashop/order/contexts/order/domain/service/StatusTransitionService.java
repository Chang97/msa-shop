package com.msashop.order.contexts.order.domain.service;

import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderStatus;

public class StatusTransitionService {
    public void change(Order o, OrderStatus to){
        switch(to){
            case PENDING_PAYMENT -> o.toPendingPayment();
            case PAID -> o.toPaid();
            case CANCELLED -> o.cancel();
            default -> throw new IllegalStateException("unsupported: "+to);
        }
    }
}