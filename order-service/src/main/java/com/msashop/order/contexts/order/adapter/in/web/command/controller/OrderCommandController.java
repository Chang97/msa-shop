package com.msashop.order.contexts.order.adapter.in.web.command.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.msashop.order.contexts.order.adapter.in.web.command.dto.IdResponse;
import com.msashop.order.contexts.order.adapter.in.web.command.dto.ChangeOrderStatusRequest;
import com.msashop.order.contexts.order.adapter.in.web.command.dto.ChangeOrderStatusResponse;
import com.msashop.order.contexts.order.application.command.dto.CreateOrderCommand;
import com.msashop.order.contexts.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.contexts.order.application.command.port.in.CreateOrderUseCase;
import com.msashop.order.contexts.order.application.command.port.in.ChangeOrderStatusUseCase;
import com.msashop.order.contexts.order.domain.model.OrderStatus;
import com.msashop.order.contexts.order.domain.service.StatusTransitionResult;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderCommandController {
    
    private final CreateOrderUseCase create; 
    private final ChangeOrderStatusUseCase changeOrderStatusUseCase;

    @PostMapping 
    public ResponseEntity<?> create(@RequestBody CreateOrderCommand cmd) { 
        long id = create.handle(cmd); 
        return ResponseEntity.ok(new IdResponse(id)); 
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ChangeOrderStatusResponse> changeStatus(
            @PathVariable("orderId") long orderId,
            @Valid @RequestBody ChangeOrderStatusRequest request
    ) {
        OrderStatus targetStatus = OrderStatus.valueOf(request.status().toUpperCase());
        StatusTransitionResult result = changeOrderStatusUseCase.handle(
                new ChangeOrderStatusCommand(orderId, targetStatus)
        );
        ChangeOrderStatusResponse response = new ChangeOrderStatusResponse(
                result.from().name(),
                result.to().name(),
                result.releaseInventory()
        );
        return ResponseEntity.ok(response);
    }
}
