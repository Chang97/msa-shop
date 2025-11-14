package com.product_service.contexts.product.application.query.port.in;

import java.util.List;

import com.product_service.contexts.product.domain.model.Product;

public interface GetProductsUseCase {

    List<Product> handle();
}
