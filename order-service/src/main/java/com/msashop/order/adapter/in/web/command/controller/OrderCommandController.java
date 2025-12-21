package com.msashop.order.adapter.in.web.command.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msashop.order.adapter.in.web.command.dto.ChangeOrderStatusRequest;
import com.msashop.order.adapter.in.web.command.dto.ChangeOrderStatusResponse;
import com.msashop.order.adapter.in.web.command.dto.CreateOrderRequest;
import com.msashop.order.adapter.in.web.command.dto.IdResponse;
import com.msashop.order.adapter.in.web.command.mapper.OrderCommandWebMapper;
import com.msashop.order.application.command.port.in.ChangeOrderStatusUseCase;
import com.msashop.order.application.command.port.in.CreateOrderUseCase;
import com.msashop.order.domain.service.StatusTransitionResult;
import com.msashop.order.infrastructure.security.CurrentUserId;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderCommandController {
    
    private final CreateOrderUseCase create; 
    private final ChangeOrderStatusUseCase changeOrderStatusUseCase;
    private final OrderCommandWebMapper mapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ORDER_CREATE')")
    public ResponseEntity<IdResponse> create(
            @CurrentUserId Long userId,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        long id = create.handle(mapper.toCommand(userId, request));
        return ResponseEntity.ok(new IdResponse(id));
    }

    @PatchMapping("/{orderId}/status")
    @PreAuthorize("hasAuthority('ORDER_UPDATE_STATUS')")
    public ResponseEntity<ChangeOrderStatusResponse> changeStatus(
            @PathVariable("orderId") long orderId,
            @Valid @RequestBody ChangeOrderStatusRequest request
    ) {
        StatusTransitionResult result = changeOrderStatusUseCase.handle(
                mapper.toCommand(orderId, request)
        );
        ChangeOrderStatusResponse response = new ChangeOrderStatusResponse(
                result.from().name(),
                result.to().name(),
                result.releaseInventory()
        );
        return ResponseEntity.ok(response);
    }
}
