package com.product_service.contexts.product.domain.model.exception;

public class InvalidProductStateException extends RuntimeException {

    public InvalidProductStateException(String message) {
        super(message);
    }
}
