package com.msashop.product.contexts.product.application.query.port.in;

import com.msashop.product.contexts.product.domain.model.Product;

public interface GetProductUseCase {

    Product handle(long id);
}
