package com.msashop.product.contexts.product.application.command.port.in;

import com.msashop.product.contexts.product.application.command.dto.ChangeStockCommand;
import com.msashop.product.contexts.product.domain.model.Product;

public interface ChangeStockUseCase {

    Product handle(ChangeStockCommand command);
}
