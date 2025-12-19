package com.msashop.order.adapter.out.persistence.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.msashop.order.adapter.out.persistence.mapper.OrderPersistenceMapper;
import com.msashop.order.adapter.out.persistence.repo.OrderRepository;
import com.msashop.order.domain.model.Order;
import com.msashop.order.domain.port.out.OrderRepositoryPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderRepository orderRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        var entity = mapper.toEntity(order);
        var saved = orderRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(long id) {
        return orderRepository.findWithItemsById(id).map(mapper::toDomain);
    }
}
