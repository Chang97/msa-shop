## 주문 이벤트 흐름

```
Client → OrderService (POST /orders)
        → OrderRepository 저장 + Outbox 기록
OutboxPublisher (스케줄)
        → PaymentPort.notifyOrderCreated
PaymentStubAdapter
        → ChangeOrderStatusUseCase (OrderStatus.PAID)
```

1. 주문 생성 시 `ORDER_CREATED` 이벤트 페이로드가 Outbox 테이블에 `PENDING` 상태로 저장됩니다.
2. `OutboxPublisher`가 주기적으로 `order.outbox.publisher.*` 설정에 따라 Outbox를 조회하고, `PaymentPort`에 이벤트를 전달합니다.
3. 현재는 `PaymentStubAdapter`가 `PaymentPort`를 구현하여 지정된 지연(`order.payment.stub-delay-ms`) 후 내부적으로 `ChangeOrderStatusUseCase`를 호출해 주문을 `PAID` 상태로 전환합니다.
4. 실패 시 Outbox 레코드는 `FAILED` 로 남아 재시도/모니터링이 가능하며, 이후 Kafka 등 외부 브로커로 교체할 경우 `PaymentPort` 구현체만 바꾸면 됩니다.
