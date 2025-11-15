package com.msashop.auth.contexts.identity.auth.application.port.in;

import com.msashop.auth.contexts.identity.auth.application.dto.LogoutCommand;
import com.msashop.auth.contexts.identity.auth.application.dto.LogoutExecutionResult;

public interface LogoutUseCase {

    LogoutExecutionResult handle(LogoutCommand command);

}
