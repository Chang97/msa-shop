package com.msashop.product.application.command.port.in;

import com.msashop.product.application.command.dto.ChangeStockCommand;
import com.msashop.product.application.command.dto.ProductDetailResult;

public interface ChangeStockUseCase {

    ProductDetailResult handle(ChangeStockCommand command);
}
