package com.msashop.order.contexts.order.application.query.mapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.msashop.order.contexts.order.application.query.dto.OrderItemView;
import com.msashop.order.contexts.order.application.query.dto.OrderView;
import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderItem;

@Component
public class OrderQueryMapper {

    public OrderView toView(Order order) {
        return new OrderView(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getTotal().value(),
                order.getCreatedAt(),
                toItemViews(order.getItems())
        );
    }

    private List<OrderItemView> toItemViews(List<OrderItem> items) {
        return items.stream()
                .map(this::toItemView)
                .collect(Collectors.toList());
    }

    private OrderItemView toItemView(OrderItem item) {
        return new OrderItemView(
                null,
                item.productId(),
                item.productName(),
                item.unitPrice().value(),
                item.qty(),
                item.lineAmount().value()
        );
    }
}
