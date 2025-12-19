package com.msashop.product.application.command.port.in;

import com.msashop.product.application.command.dto.CreateProductCommand;
import com.msashop.product.application.command.dto.CreateProductResult;

public interface CreateProductUseCase {

    CreateProductResult handle(CreateProductCommand command);
}
