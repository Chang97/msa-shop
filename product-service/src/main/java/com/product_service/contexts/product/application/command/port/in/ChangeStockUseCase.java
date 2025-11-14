package com.product_service.contexts.product.application.command.port.in;

import com.product_service.contexts.product.application.command.dto.ChangeStockCommand;
import com.product_service.contexts.product.domain.model.Product;

public interface ChangeStockUseCase {

    Product handle(ChangeStockCommand command);
}
