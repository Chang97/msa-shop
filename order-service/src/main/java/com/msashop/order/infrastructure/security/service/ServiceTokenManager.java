package com.msashop.order.infrastructure.security.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceTokenManager {

    private static final Logger log = LoggerFactory.getLogger(ServiceTokenManager.class);

    private final ServiceTokenClient serviceTokenClient;
    private final ServiceClientProperties properties;

    private volatile CachedToken cachedToken;

    public ServiceTokenManager(ServiceTokenClient serviceTokenClient, ServiceClientProperties properties) {
        this.serviceTokenClient = serviceTokenClient;
        this.properties = properties;
    }

    public String accessToken() {
        CachedToken token = cachedToken;
        if (token != null && !token.isExpired()) {
            return token.value();
        }
        synchronized (this) {
            token = cachedToken;
            if (token == null || token.isExpired()) {
                cachedToken = fetchNewToken();
            }
            return cachedToken.value();
        }
    }

    public String authorizationHeaderValue() {
        return "Bearer " + accessToken();
    }

    private CachedToken fetchNewToken() {
        ServiceTokenClient.TokenResponse response = serviceTokenClient.requestToken();
        if (response == null || response.accessToken() == null) {
            throw new IllegalStateException("서비스 토큰을 발급받을 수 없습니다.");
        }
        long expiresIn = Math.max(response.expiresIn(), 0);
        long skew = Math.max(properties.getRenewalSkewSeconds(), 0);
        long effectiveSkew = Math.min(skew, expiresIn);
        Instant expiresAt = Instant.now().plusSeconds(expiresIn).minusSeconds(effectiveSkew);
        log.info("서비스 토큰을 갱신했습니다. clientId={}, 갱신 주기={}s", properties.getClientId(), expiresIn);
        return new CachedToken(response.accessToken(), expiresAt);
    }

    private record CachedToken(String value, Instant expiresAt) {
        boolean isExpired() {
            return expiresAt == null || Instant.now().isAfter(expiresAt);
        }
    }
}
