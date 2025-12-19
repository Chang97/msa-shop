package com.msashop.order.adapter.out.external.product;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "clients.product")
public class ProductClientProperties {
    private String baseUrl;
    private final Paths paths = new Paths();

    @Getter @Setter
    public static class Paths {
        private String detail = "/products/{id}";
        private String stock = "/products/{id}/stock";
        private String health = "/products/health";
    }
}
