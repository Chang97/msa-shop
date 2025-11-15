package com.msashop.product.contexts.product.application.query.port.in;

import java.util.List;

import com.msashop.product.contexts.product.domain.model.Product;

public interface GetProductsUseCase {

    List<Product> handle();
}
