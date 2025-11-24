package com.msashop.order.contexts.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msashop.order.contexts.order.domain.port.out.ProductInventoryPort;
import com.msashop.order.contexts.order.domain.service.StatusTransitionService;

@Configuration
public class OrderServiceConfig {

    @Bean
    public StatusTransitionService statusTransitionService(ProductInventoryPort productInventoryPort) {
        return new StatusTransitionService(productInventoryPort);
    }
}
