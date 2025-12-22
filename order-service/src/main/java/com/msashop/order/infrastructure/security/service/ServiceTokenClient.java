package com.msashop.order.infrastructure.security.service;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Component
public class ServiceTokenClient {

    private final RestClient restClient;
    private final ServiceClientProperties properties;

    public ServiceTokenClient(RestClient.Builder builder, ServiceClientProperties properties) {
        this.restClient = builder.build();
        this.properties = properties;
    }

    public TokenResponse requestToken() {
        validateProperties();
        ServiceTokenRequest request = new ServiceTokenRequest(properties.getClientId(), properties.getClientSecret());
        return restClient.post()
                .uri(properties.getTokenUrl())
                .header(properties.getInternalSecretHeader(), properties.getInternalSecret())
                .body(request)
                .retrieve()
                .body(TokenResponse.class);
    }

    private void validateProperties() {
        if (!StringUtils.hasText(properties.getTokenUrl())) {
            throw new IllegalStateException("security.service-client.token-url 설정이 필요합니다.");
        }
        if (!StringUtils.hasText(properties.getClientId()) || !StringUtils.hasText(properties.getClientSecret())) {
            throw new IllegalStateException("서비스 토큰 발급용 clientId/clientSecret 설정이 필요합니다.");
        }
        if (!StringUtils.hasText(properties.getInternalSecret())) {
            throw new IllegalStateException("security.service-client.internal-secret 설정이 필요합니다.");
        }
    }

    private record ServiceTokenRequest(String clientId, String clientSecret) {}

    public record TokenResponse(String accessToken, long expiresIn) {}
}
