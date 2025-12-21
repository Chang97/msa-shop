package com.msashop.auth.identity.auth.application.port.in;

import com.msashop.auth.identity.auth.application.dto.AuthExecutionResult;
import com.msashop.auth.identity.auth.application.dto.RefreshTokenCommand;

public interface RefreshTokenUseCase {

    AuthExecutionResult handle(RefreshTokenCommand command);
}
