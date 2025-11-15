package com.msashop.auth.platform.redis.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.auth.authority")
public record AuthorityCacheProperties(
        long cacheTtlSeconds,
        String keyPrefix
) {
}