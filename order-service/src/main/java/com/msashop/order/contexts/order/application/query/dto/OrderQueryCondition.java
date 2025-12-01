package com.msashop.order.contexts.order.application.query.dto;

import java.time.OffsetDateTime;
import java.util.List;

import com.msashop.order.contexts.order.domain.model.OrderStatus;

public record OrderQueryCondition(
    Long userId,
    List<OrderStatus> statuses,
    OffsetDateTime from,
    OffsetDateTime to,
    int page,
    int size
) {}