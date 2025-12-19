package com.msashop.order.application.command.handler;

import org.springframework.stereotype.Service;

import com.msashop.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.application.command.port.in.ChangeOrderStatusUseCase;
import com.msashop.order.domain.model.Order;
import com.msashop.order.domain.model.OrderItem;
import com.msashop.order.domain.model.exception.OrderNotFoundException;
import com.msashop.order.domain.port.out.OrderRepositoryPort;
import com.msashop.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.domain.service.StatusTransitionResult;
import com.msashop.order.domain.service.StatusTransitionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeOrderStatusHandler implements ChangeOrderStatusUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final ProductInventoryPort productInventoryPort;
    private final StatusTransitionService statusTransitionService;

    @Override
    public StatusTransitionResult handle(ChangeOrderStatusCommand command) {
        Order order = orderRepositoryPort.findById(command.orderId())
                .orElseThrow(() -> new OrderNotFoundException(command.orderId()));

        StatusTransitionResult result = statusTransitionService.change(order, command.targetStatus());
        if (result.releaseInventory()) {
            releaseReservedInventory(order);
        }
        orderRepositoryPort.save(order);
        return result;
    }

    private void releaseReservedInventory(Order order) {
        for (OrderItem item : order.getItems()) {
            productInventoryPort.release(item.productId(), item.qty());
        }
    }
}
