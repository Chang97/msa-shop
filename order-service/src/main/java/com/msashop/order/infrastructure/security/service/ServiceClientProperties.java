package com.msashop.order.infrastructure.security.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.service-client")
public class ServiceClientProperties {

    private String tokenUrl;
    private String clientId;
    private String clientSecret;
    private String internalSecretHeader = "X-Internal-Secret";
    private String internalSecret;
    private long renewalSkewSeconds = 30;
}
