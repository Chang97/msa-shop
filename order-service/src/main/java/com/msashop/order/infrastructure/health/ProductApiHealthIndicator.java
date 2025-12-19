package com.msashop.order.infrastructure.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.msashop.order.adapter.out.external.product.ProductApiClient;
import com.msashop.order.adapter.out.external.product.ProductClientProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductApiHealthIndicator implements HealthIndicator {

    private final ProductApiClient productApiClient;
    private final ProductClientProperties properties;

    @Override
    public Health health() {
        boolean reachable = productApiClient.ping();
        if (reachable) {
            return Health.up()
                    .withDetail("url", properties.getBaseUrl())
                    .build();
        }
        return Health.down()
                .withDetail("url", properties.getBaseUrl())
                .build();
    }
}
