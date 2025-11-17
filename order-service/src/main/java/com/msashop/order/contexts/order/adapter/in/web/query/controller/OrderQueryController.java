package com.msashop.order.contexts.order.adapter.in.web.query.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msashop.order.contexts.order.application.query.dto.OrderView;
import com.msashop.order.contexts.order.application.query.port.in.GetOrderUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderQueryController {

    private final GetOrderUseCase getOrder;

    @GetMapping("/{id}") 
    public OrderView get(@PathVariable("id") long id) { 
        return getOrder.handle(id); 
    }
}
