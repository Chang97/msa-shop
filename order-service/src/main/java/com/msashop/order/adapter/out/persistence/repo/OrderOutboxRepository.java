package com.msashop.order.adapter.out.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msashop.order.adapter.out.persistence.entity.OrderOutboxEntity;


public interface OrderOutboxRepository extends JpaRepository<OrderOutboxEntity, Long> {

    List<OrderOutboxEntity> findByStatusOrderByCreatedAtAsc(String status);
    long countByStatus(String status);

    @Query(value="""
    select * 
    from order_outbox 
    where status='PENDING' 
    order by created_at for update skip locked limit :limit
    """, nativeQuery=true)
    List<OrderOutboxEntity> findPendingForUpdate(@Param("limit") int limit);
}
