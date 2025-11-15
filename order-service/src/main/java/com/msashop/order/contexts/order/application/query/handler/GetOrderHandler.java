package com.msashop.order.contexts.order.application.query.handler;

import org.springframework.stereotype.Service;

import com.msashop.order.contexts.order.application.query.dto.OrderView;
import com.msashop.order.contexts.order.application.query.mapper.OrderQueryMapper;
import com.msashop.order.contexts.order.application.query.port.in.GetOrderUseCase;
import com.msashop.order.contexts.order.domain.port.out.OrderRepositoryPort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrderHandler implements GetOrderUseCase {
    private final OrderRepositoryPort port;
    private final OrderQueryMapper mapper;

    @Override
    @Transactional
    public OrderView handle(long id) {
        return port.findById(id)
                .map(mapper::toView)
                .orElseThrow();
    }
}
