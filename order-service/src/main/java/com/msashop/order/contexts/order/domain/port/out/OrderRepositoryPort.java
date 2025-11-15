package com.msashop.order.contexts.order.domain.port.out;

import java.util.Optional;

import com.msashop.order.contexts.order.domain.model.Order;

public interface OrderRepositoryPort { 
    Order save(Order o); 
    Optional<Order> findById(long id); 
}

