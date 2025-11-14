package com.product_service.contexts.product.application.command.port.in;

import com.product_service.contexts.product.application.command.dto.CreateProductCommand;
import com.product_service.contexts.product.application.command.dto.CreateProductResult;

public interface CreateProductUseCase {

    CreateProductResult handle(CreateProductCommand command);
}
