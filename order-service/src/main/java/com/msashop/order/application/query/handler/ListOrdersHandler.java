package com.msashop.order.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.order.application.query.dto.OrderQueryCondition;
import com.msashop.order.application.query.dto.OrderSummaryView;
import com.msashop.order.application.query.dto.PageResult;
import com.msashop.order.application.query.port.in.ListOrdersUseCase;
import com.msashop.order.application.query.port.out.OrderQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListOrdersHandler implements ListOrdersUseCase {
    private final OrderQueryPort orderQueryPort;

    public PageResult<OrderSummaryView> handle(OrderQueryCondition condition) {
        return orderQueryPort.findOrders(condition);
    }
}