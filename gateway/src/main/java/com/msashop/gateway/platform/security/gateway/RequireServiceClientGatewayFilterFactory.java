package com.msashop.gateway.platform.security.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RequireServiceClientGatewayFilterFactory
        extends AbstractGatewayFilterFactory<RequireServiceClientGatewayFilterFactory.Config> {

    private static final String SERVICE_ID_HEADER = "X-Service-Id";

    public RequireServiceClientGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String serviceId = exchange.getRequest().getHeaders().getFirst(SERVICE_ID_HEADER);
            if (!StringUtils.hasText(serviceId) || !serviceId.equals(config.getServiceId())) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    @Override
    public java.util.List<String> shortcutFieldOrder() {
        return java.util.List.of("serviceId");
    }

    public static class Config {
        private String serviceId;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }
    }
}
