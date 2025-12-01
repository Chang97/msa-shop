package com.msashop.order.contexts.order.application.query.port.in;

import com.msashop.order.contexts.order.application.query.dto.OrderQueryCondition;
import com.msashop.order.contexts.order.application.query.dto.OrderSummaryView;
import com.msashop.order.contexts.order.application.query.dto.PageResult;

public interface ListOrdersUseCase {
    PageResult<OrderSummaryView> handle(OrderQueryCondition condition);
}
