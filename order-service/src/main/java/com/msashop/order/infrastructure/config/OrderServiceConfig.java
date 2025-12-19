package com.msashop.order.infrastructure.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msashop.order.domain.service.StatusTransitionService;

@Configuration
public class OrderServiceConfig {

    @Bean
    public StatusTransitionService statusTransitionService() {
        return new StatusTransitionService();
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
