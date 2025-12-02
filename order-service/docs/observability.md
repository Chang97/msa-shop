## 관측 구성 안내

### Actuator & Prometheus
- `spring-boot-starter-actuator`, `micrometer-registry-prometheus` 의존성을 추가했습니다.
- `/actuator/health`, `/actuator/info`, `/actuator/metrics`, `/actuator/prometheus` 엔드포인트가 `/api` 컨텍스트 하위로 노출됩니다.
- `management.metrics.tags.application=order-service` 로 공통 태그를 부여하여 대시보드에서 서비스별 필터링이 쉽습니다.

### Outbox 메트릭
- `order_outbox_published_total` : 성공적으로 발행된 Outbox 이벤트 수 (현재 `ORDER_CREATED` 태그만 사용).
- `order_outbox_failed_total` : 발행 실패 카운터.
- `order_outbox_publish_latency` : Outbox 배치 실행 시간을 측정하는 Timer.
- `order_outbox_pending` : 현재 `PENDING` 상태인 레코드 수를 나타내는 Gauge.

### Health Indicator
- 기본 Health 정보 외에 `ProductApiHealthIndicator`가 추가되어 `/actuator/health` 응답에 `productApi` 항목으로 표기됩니다.
- Product 서비스의 `/products/health` 엔드포인트 호출 성공 여부로 up/down 상태를 판단합니다.

### Prometheus 스크레이프 예시
```yaml
scrape_configs:
  - job_name: 'order-service'
    metrics_path: '/api/actuator/prometheus'
    static_configs:
      - targets: ['order-service:8083']
```

### 참고 사항
- Grafana에서 `order-service` 태그를 기준으로 대시보드를 구성하면 다른 서비스 지표와 쉽게 구분할 수 있습니다.
- `order_outbox_pending` 값이 계속 증가하거나 0으로 돌아오지 않으면 Outbox 처리 지연 가능성이 있으니 경고 임계치를 설정해 두는 것이 좋습니다.
