package com.order_service.contexts.order.adapter.out.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.contexts.order.adapter.out.persistence.entity.OrderOutboxEntity;

public interface OrderOutboxRepository extends JpaRepository<OrderOutboxEntity, Long> {

    List<OrderOutboxEntity> findByStatusOrderByCreatedAtAsc(String status);
}
