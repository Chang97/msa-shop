package com.msashop.order.contexts.order.application.command.handler;

import org.springframework.stereotype.Service;

import com.msashop.order.contexts.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.contexts.order.application.command.port.in.ChangeOrderStatusUseCase;
import com.msashop.order.contexts.order.domain.model.Order;
import com.msashop.order.contexts.order.domain.port.out.OrderRepositoryPort;
import com.msashop.order.contexts.order.domain.service.StatusTransitionResult;
import com.msashop.order.contexts.order.domain.service.StatusTransitionService;
import com.msashop.order.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangeOrderStatusHandler implements ChangeOrderStatusUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final StatusTransitionService statusTransitionService;

    @Override
    public StatusTransitionResult handle(ChangeOrderStatusCommand command) {
        Order order = orderRepositoryPort.findById(command.orderId())
                .orElseThrow(() -> new NotFoundException("Order not found: " + command.orderId()));

        StatusTransitionResult result = statusTransitionService.change(order, command.targetStatus());
        orderRepositoryPort.save(order);
        return result;
    }
}
