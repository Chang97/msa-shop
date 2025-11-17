package com.msashop.order.contexts.order.application.command.dto;

public record OrderCreatedEventPayload(long orderId, long userId) {}
