package com.msashop.order.contexts.order.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

    @Bean(destroyMethod = "shutdown")
    public ScheduledExecutorService paymentStubExecutor() {
        return Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r);
            t.setName("payment-stub");
            t.setDaemon(true);
            return t;
        });
    }
}
