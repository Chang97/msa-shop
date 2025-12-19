package com.msashop.order.domain.model.exception;

public class ProductUnavailableException extends RuntimeException {

    public ProductUnavailableException(long productId) {
        super("Product unavailable: " + productId);
    }

    public ProductUnavailableException(String message) {
        super(message);
    }
}
