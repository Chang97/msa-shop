package com.msashop.order.application.query.port.out;

import com.msashop.order.application.query.dto.OrderQueryCondition;
import com.msashop.order.application.query.dto.OrderSummaryView;
import com.msashop.order.application.query.dto.PageResult;

public interface OrderQueryPort {
    PageResult<OrderSummaryView> findOrders(OrderQueryCondition condition);
}
