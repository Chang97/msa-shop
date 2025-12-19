package com.msashop.order.application.query.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderView (
    Long id,
    String orderNumber, 
    String status, 
    BigDecimal totalAmount, 
    OffsetDateTime createdAt, 
    List<OrderItemView> items
) {}

