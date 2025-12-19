package com.msashop.product.domain.model.exception;

public class InvalidProductStateException extends RuntimeException {

    public InvalidProductStateException(String message) {
        super(message);
    }
}
