package com.msashop.product.contexts.product.application.query.port.in;

import java.util.List;

import com.msashop.product.contexts.product.application.query.dto.ProductView;

public interface GetProductsUseCase {

    List<ProductView> handle();
}
