package com.msashop.order.contexts.order.application.query.port.in;

import com.msashop.order.contexts.order.application.query.dto.OrderView;

public interface GetOrderUseCase {
    OrderView handle(long id);
}
