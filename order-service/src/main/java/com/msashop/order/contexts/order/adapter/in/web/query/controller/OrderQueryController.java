package com.msashop.order.contexts.order.adapter.in.web.query.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msashop.order.contexts.order.application.query.dto.OrderQueryCondition;
import com.msashop.order.contexts.order.application.query.dto.OrderSummaryView;
import com.msashop.order.contexts.order.application.query.dto.OrderView;
import com.msashop.order.contexts.order.application.query.dto.PageResult;
import com.msashop.order.contexts.order.application.query.port.in.GetOrderUseCase;
import com.msashop.order.contexts.order.application.query.port.in.ListOrdersUseCase;
import com.msashop.order.contexts.order.domain.model.OrderStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderQueryController {

    private final GetOrderUseCase getOrder;
    private final ListOrdersUseCase listOrdersUseCase;

    @GetMapping("/{id}") 
    public OrderView get(@PathVariable("id") long id) { 
        return getOrder.handle(id); 
    }

    @GetMapping
    public PageResult<OrderSummaryView> list(
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "status", required = false) List<OrderStatus> status,
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime to,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        OrderQueryCondition condition = new OrderQueryCondition(userId, status, from, to, page, size);
        return listOrdersUseCase.handle(condition);
    }
}
