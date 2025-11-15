package com.msashop.order.contexts.order.adapter.out.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
