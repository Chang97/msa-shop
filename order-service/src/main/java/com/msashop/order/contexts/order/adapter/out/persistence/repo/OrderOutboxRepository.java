package com.msashop.order.contexts.order.adapter.out.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msashop.order.contexts.order.adapter.out.persistence.entity.OrderOutboxEntity;

public interface OrderOutboxRepository extends JpaRepository<OrderOutboxEntity, Long> {

    List<OrderOutboxEntity> findByStatusOrderByCreatedAtAsc(String status);

    @Query(value="""
    select * 
    from order_outbox 
    where status='PENDING' 
    order by created_at for update skip locked limit :limit
    """, nativeQuery=true)
    List<OrderOutboxEntity> findPendingForUpdate(int limit);
}
