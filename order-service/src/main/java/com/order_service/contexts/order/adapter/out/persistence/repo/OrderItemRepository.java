package com.order_service.contexts.order.adapter.out.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.contexts.order.adapter.out.persistence.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findByOrderId(Long orderId);
}
