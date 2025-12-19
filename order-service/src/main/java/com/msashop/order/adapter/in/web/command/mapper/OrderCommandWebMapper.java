package com.msashop.order.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.order.adapter.in.web.command.dto.ChangeOrderStatusRequest;
import com.msashop.order.adapter.in.web.command.dto.CreateOrderRequest;
import com.msashop.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.application.command.dto.CreateOrderCommand;
import com.msashop.order.application.command.dto.CreateOrderCommand.Item;
import com.msashop.order.domain.model.OrderStatus;

@Component
public class OrderCommandWebMapper {

    public CreateOrderCommand toCommand(Long userId, CreateOrderRequest request) {
        return new CreateOrderCommand(
                userId,
                request.receiverName(),
                request.receiverPhone(),
                request.postcode(),
                request.address1(),
                request.address2(),
                request.items().stream()
                        .map(item -> new Item(
                                item.productId(),
                                item.productName(),
                                item.unitPrice(),
                                item.qty()
                        ))
                        .toList()
        );
    }

    public ChangeOrderStatusCommand toCommand(long orderId, ChangeOrderStatusRequest request) {
        OrderStatus targetStatus = OrderStatus.valueOf(request.status().toUpperCase());
        return new ChangeOrderStatusCommand(orderId, targetStatus);
    }
}
