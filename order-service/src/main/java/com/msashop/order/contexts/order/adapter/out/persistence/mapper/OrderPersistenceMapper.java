package com.msashop.order.contexts.order.adapter.out.persistence.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderEntity;
import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderItemEntity;
import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.model.OrderItem;
import com.msashop.order.contexts.order.domain.model.OrderStatus;
import com.msashop.order.contexts.order.domain.model.vo.Address;
import com.msashop.order.contexts.order.domain.model.vo.Money;

@Component
public class OrderPersistenceMapper {

    public OrderEntity toEntity(Order order) {
        String orderNumber = requireOrderNumber(order);
        OrderEntity entity = new OrderEntity(orderNumber, order.getUserId());
        entity.setId(order.getId());
        entity.setStatus(order.getStatus().name());
        entity.setSubtotalAmount(order.getSubtotal().value());
        entity.setDiscountAmount(order.getDiscount().value());
        entity.setShippingFee(order.getShipping().value());
        entity.setTotalAmount(order.getTotal().value());
        entity.setReceiverName(order.getReceiverName());
        entity.setReceiverPhone(order.getReceiverPhone());
        entity.setShippingPostcode(order.getShippingAddress().postcode());
        entity.setShippingAddress1(order.getShippingAddress().address1());
        entity.setShippingAddress2(order.getShippingAddress().address2());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());

        order.getItems().forEach(item -> entity.addItem(toItemEntity(item)));
        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        Address address = new Address(
                entity.getShippingPostcode(),
                entity.getShippingAddress1(),
                entity.getShippingAddress2()
        );
        Order order = new Order(entity.getUserId(), entity.getReceiverName(), entity.getReceiverPhone(), address);
        order.setId(entity.getId());
        order.setOrderNumber(entity.getOrderNumber());
        entity.getItems().forEach(item -> order.addItem(
                new OrderItem(
                        item.getProductId(),
                        item.getProductName(),
                        new Money(item.getUnitPrice()),
                        item.getQty()
                )
        ));
        order.changeShippingFee(new Money(entity.getShippingFee()));
        order.applyDiscount(new Money(entity.getDiscountAmount()));
        order.restoreStatus(OrderStatus.valueOf(entity.getStatus()));
        order.setCreatedAt(entity.getCreatedAt());
        order.setUpdatedAt(entity.getUpdatedAt());
        return order;
    }

    private OrderItemEntity toItemEntity(OrderItem item) {
        return new OrderItemEntity(
                item.productId(),
                item.productName(),
                item.unitPrice().value(),
                item.qty()
        );
    }

    private String requireOrderNumber(Order order) {
        if (order.getOrderNumber() != null && !order.getOrderNumber().isBlank()) {
            return order.getOrderNumber();
        }
        String generated = UUID.randomUUID().toString().replace("-", "");
        order.setOrderNumber(generated);
        return generated;
    }
}
