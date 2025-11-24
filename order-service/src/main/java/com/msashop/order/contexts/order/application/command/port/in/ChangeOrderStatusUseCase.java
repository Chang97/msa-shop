package com.msashop.order.contexts.order.application.command.port.in;

import com.msashop.order.contexts.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.contexts.order.domain.service.StatusTransitionResult;

public interface ChangeOrderStatusUseCase {
    StatusTransitionResult handle(ChangeOrderStatusCommand command);
}
