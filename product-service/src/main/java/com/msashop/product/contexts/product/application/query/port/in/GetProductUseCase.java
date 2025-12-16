package com.msashop.product.contexts.product.application.query.port.in;

import com.msashop.product.contexts.product.application.query.dto.ProductView;

public interface GetProductUseCase {

    ProductView handle(long id);
}
