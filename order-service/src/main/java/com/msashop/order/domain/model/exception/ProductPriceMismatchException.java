package com.msashop.order.domain.model.exception;

public class ProductPriceMismatchException extends RuntimeException {

    public ProductPriceMismatchException(long productId) {
        super("Product price mismatch: " + productId);
    }
}
