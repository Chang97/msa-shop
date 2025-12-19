package com.msashop.order.application.command.port.in;

import com.msashop.order.application.command.dto.ChangeOrderStatusCommand;
import com.msashop.order.domain.service.StatusTransitionResult;

public interface ChangeOrderStatusUseCase {
    StatusTransitionResult handle(ChangeOrderStatusCommand command);
}
