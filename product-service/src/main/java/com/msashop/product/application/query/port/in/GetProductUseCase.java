package com.msashop.product.application.query.port.in;

import com.msashop.product.application.query.dto.ProductView;

public interface GetProductUseCase {

    ProductView handle(long id);
}
