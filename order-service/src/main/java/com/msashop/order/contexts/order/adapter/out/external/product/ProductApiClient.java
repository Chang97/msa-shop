package com.msashop.order.contexts.order.adapter.out.external.product;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ProductApiClient {
    private final RestClient restClient;
    private final ProductClientProperties properties;

    @SuppressWarnings("null")
    public ProductApiClient(RestClient.Builder builder, ProductClientProperties properties) {
        if (properties.getBaseUrl() == null || properties.getBaseUrl().isBlank()) {
            throw new IllegalStateException("clients.product.base-url 설정이 필요합니다.");
        }
        this.properties = properties;
        this.restClient = builder.baseUrl(properties.getBaseUrl()).build();
    }

    @SuppressWarnings("null")
    public Optional<ProductResponse> getProduct(long productId) {
        try {
            ProductResponse body = restClient.get()
                    .uri(properties.getPaths().getDetail(), productId)
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
    public void changeStock(long productId, int delta) {
        try {
            restClient.patch()
                    .uri(properties.getPaths().getStock(), productId)
                    .contentType(MediaType.APPLICATION_JSON)
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
}
