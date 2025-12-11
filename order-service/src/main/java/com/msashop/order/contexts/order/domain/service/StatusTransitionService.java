package com.msashop.order.contexts.order.domain.service;

import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderItem;
import com.msashop.order.contexts.order.domain.model.OrderStatus;
import com.msashop.order.contexts.order.domain.port.out.ProductInventoryPort;

public class StatusTransitionService {

    private final ProductInventoryPort productInventoryPort;

    public StatusTransitionService(ProductInventoryPort productInventoryPort) {
        this.productInventoryPort = productInventoryPort;
    }

    public StatusTransitionResult change(Order order, OrderStatus to) {
        OrderStatus from = order.getStatus();
        switch (to) {
            case PENDING_PAYMENT -> order.toPendingPayment();
            case PAID -> order.toPaid();
            case FULFILLED -> order.toFulfilled();
            case CANCELLED -> order.cancel();
            default -> throw new IllegalStateException("unsupported: " + to);
        }
        boolean releaseInventory = shouldReleaseInventory(from, order.getStatus(), order.isInventoryReserved());
        if (releaseInventory) {
            releaseReservedInventory(order);
        }
        return new StatusTransitionResult(from, order.getStatus(), releaseInventory);
    }

    private boolean shouldReleaseInventory(OrderStatus from, OrderStatus to, boolean inventoryReserved) {
        if (!inventoryReserved) {
            return false;
        }
        return to == OrderStatus.CANCELLED &&
                (from == OrderStatus.CREATED || from == OrderStatus.PENDING_PAYMENT);
    }

    private void releaseReservedInventory(Order order) {
        for (OrderItem item : order.getItems()) {
            productInventoryPort.release(item.productId(), item.qty());
        }
        order.releaseInventoryReservation();
    }
}
