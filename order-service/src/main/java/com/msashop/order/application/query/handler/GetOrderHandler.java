package com.msashop.order.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.order.application.query.dto.OrderView;
import com.msashop.order.application.query.mapper.OrderQueryMapper;
import com.msashop.order.application.query.port.in.GetOrderUseCase;
import com.msashop.order.domain.port.out.OrderRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetOrderHandler implements GetOrderUseCase {
    private final OrderRepositoryPort port;
    private final OrderQueryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public OrderView handle(long id) {
        return port.findById(id)
                .map(mapper::toView)
                .orElseThrow();
    }
}
