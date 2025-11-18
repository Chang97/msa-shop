package com.msashop.order.contexts.order.domain.service;

import com.msashop.order.contexts.order.domain.model.OrderStatus;

public record StatusTransitionResult(OrderStatus from, OrderStatus to, boolean releaseInventory) {
}
