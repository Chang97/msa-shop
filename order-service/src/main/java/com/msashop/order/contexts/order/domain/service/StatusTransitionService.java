package com.msashop.order.contexts.order.domain.service;

import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderStatus;

public class StatusTransitionService {

    public StatusTransitionResult change(Order order, OrderStatus to) {
        OrderStatus from = order.getStatus();
        switch (to) {
            case PENDING_PAYMENT -> order.toPendingPayment();
            case PAID -> order.toPaid();
            case CANCELLED -> order.cancel();
            default -> throw new IllegalStateException("unsupported: " + to);
        }
        boolean releaseInventory = shouldReleaseInventory(from, order.getStatus(), order.isInventoryReserved());
        return new StatusTransitionResult(from, order.getStatus(), releaseInventory);
    }

    private boolean shouldReleaseInventory(OrderStatus from, OrderStatus to, boolean inventoryReserved) {
        if (!inventoryReserved) {
            return false;
        }
        return to == OrderStatus.CANCELLED &&
                (from == OrderStatus.CREATED || from == OrderStatus.PENDING_PAYMENT);
    }
}
