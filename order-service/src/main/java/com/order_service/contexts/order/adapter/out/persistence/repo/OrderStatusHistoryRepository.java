package com.order_service.contexts.order.adapter.out.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.contexts.order.adapter.out.persistence.entity.OrderStatusHistoryEntity;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryEntity, Long> {

    List<OrderStatusHistoryEntity> findByOrderIdOrderByChangedAtAsc(Long orderId);
}
