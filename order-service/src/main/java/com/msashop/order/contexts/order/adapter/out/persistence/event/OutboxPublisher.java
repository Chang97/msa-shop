package com.msashop.order.contexts.order.adapter.out.persistence.event;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.msashop.order.contexts.order.adapter.out.persistence.repo.OrderOutboxRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
  private final OrderOutboxRepository repo;
  
  @Scheduled(fixedDelay=1000L)
  public void publish(){
    var batch = repo.findPendingForUpdate(100);
    // TODO: 실제 Kafka/이벤트 발행
    batch.forEach(e -> { 
        e.setStatus("PUBLISHED"); 
        e.setPublishedAt(java.time.OffsetDateTime.now()); 
    });
    repo.saveAll(batch);
  }
}