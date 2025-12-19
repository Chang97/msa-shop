package com.msashop.order.domain.service;

import com.msashop.order.domain.model.OrderStatus;

public record StatusTransitionResult(OrderStatus from, OrderStatus to, boolean releaseInventory) {
}
