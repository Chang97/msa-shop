package com.msashop.order.application.query.port.in;

import com.msashop.order.application.query.dto.OrderView;

public interface GetOrderUseCase {
    OrderView handle(long id);
}
