package com.msashop.order.adapter.out.external.product;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import com.msashop.order.infrastructure.security.current.CurrentUserProvider;
import com.msashop.order.infrastructure.security.service.ServiceTokenManager;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class ProductApiClient {
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String PERMISSIONS_HEADER = "X-User-Permissions";

    private final RestClient restClient;
    private final ProductClientProperties properties;
    private final CurrentUserProvider currentUserProvider;
    private final ServiceTokenManager serviceTokenManager;

    public ProductApiClient(RestClient.Builder builder,
            ProductClientProperties properties,
            CurrentUserProvider currentUserProvider,
            ServiceTokenManager serviceTokenManager) {
        if (properties.getBaseUrl() == null || properties.getBaseUrl().isBlank()) {
            throw new IllegalStateException("clients.product.base-url 설정이 필요합니다.");
        }
        this.properties = properties;
        this.currentUserProvider = currentUserProvider;
        this.serviceTokenManager = serviceTokenManager;
        this.restClient = builder.baseUrl(properties.getBaseUrl()).build();
    }

    @SuppressWarnings("null")
    @CircuitBreaker(name = "productClient", fallbackMethod = "getProductFallback")
    @Retry(name = "productClient")
    public Optional<ProductResponse> getProduct(long productId) {
        try {
            ProductResponse body = restClient.get()
                    .uri(properties.getPaths().getDetail(), productId)
                    .header(HttpHeaders.AUTHORIZATION, serviceTokenManager.authorizationHeaderValue())
                    .headers(this::applyUserHeaders)
                    .retrieve()
                    .body(ProductResponse.class);

            return Optional.ofNullable(body);
        } catch (RestClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                return Optional.empty();
            }
            throw toApiException("상품 조회 실패 productId=" + productId, e);
        } catch (RestClientException e) {
            throw new ProductApiException(ErrorType.IO, "상품 조회 실패 productId=" + productId, e);
        }
    }

    @SuppressWarnings("null")
    @CircuitBreaker(name = "productClient", fallbackMethod = "changeStockFallback")
    @Retry(name = "productClient")
    public void changeStock(long productId, int delta) {
        try {
            restClient.patch()
                    .uri(properties.getPaths().getStock(), productId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, serviceTokenManager.authorizationHeaderValue())
                    .headers(headers -> applyUserHeaders(headers))
                    .body(new ChangeStockRequest(delta))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw toApiException("재고 변경 실패 productId=" + productId + ", delta=" + delta, e);
        } catch (RestClientException e) {
            throw new ProductApiException(ErrorType.IO,
                    "재고 변경 실패 productId=" + productId + ", delta=" + delta, e);
        }
    }

    public boolean ping() {
        try {
            restClient.get()
                    .uri(properties.getPaths().getHealth())
                    .header(HttpHeaders.AUTHORIZATION, serviceTokenManager.authorizationHeaderValue())
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientException ex) {
            return false;
        }
    }

    record ProductResponse(Long id, String name, BigDecimal price, Integer stock) {}
    record ChangeStockRequest(int delta) {}

    private ProductApiException toApiException(String message, RestClientResponseException e) {
        return new ProductApiException(mapStatus(e.getStatusCode().value()), message, e);
    }

    private ErrorType mapStatus(int status) {
        return switch (status) {
            case 404 -> ErrorType.NOT_FOUND;
            case 400, 409 -> ErrorType.CONFLICT;
            case 500, 502, 503, 504 -> ErrorType.SERVER;
            default -> ErrorType.UNKNOWN;
        };
    }

    public enum ErrorType {
        NOT_FOUND,
        CONFLICT,
        SERVER,
        IO,
        UNKNOWN
    }

    public static class ProductApiException extends RuntimeException {
        private final ErrorType errorType;

        public ProductApiException(ErrorType errorType, String message, Throwable cause) {
            super(message, cause);
            this.errorType = errorType;
        }

        public ErrorType errorType() {
            return errorType;
        }
    }

    private Optional<ProductResponse> getProductFallback(long productId, Throwable ex) {
        throw new IllegalStateException("상품 정보를 조회할 수 없습니다. 잠시 후 다시 시도해주세요.", ex);
    }

    private void changeStockFallback(long productId, int delta, Throwable ex) {
        throw new IllegalStateException("재고 변경을 수행할 수 없습니다. 잠시 후 다시 시도해주세요.", ex);
    }

    private void applyUserHeaders(HttpHeaders headers) {
        String userId = currentUserProvider.userIdOrNull();
        if (userId != null) {
            headers.set(USER_ID_HEADER, userId);
        }
        String permissions = currentUserProvider.permissionsHeader();
        if (permissions != null && !permissions.isBlank()) {
            headers.set(PERMISSIONS_HEADER, permissions);
        }
    }
}
