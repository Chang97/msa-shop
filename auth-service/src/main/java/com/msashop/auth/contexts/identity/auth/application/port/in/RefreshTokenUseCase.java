package com.msashop.auth.contexts.identity.auth.application.port.in;

import com.msashop.auth.contexts.identity.auth.application.dto.AuthExecutionResult;
import com.msashop.auth.contexts.identity.auth.application.dto.RefreshTokenCommand;

public interface RefreshTokenUseCase {

    AuthExecutionResult handle(RefreshTokenCommand command);
}
