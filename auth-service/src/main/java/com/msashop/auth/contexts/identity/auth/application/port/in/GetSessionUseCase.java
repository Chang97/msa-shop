package com.msashop.auth.contexts.identity.auth.application.port.in;

import com.msashop.auth.contexts.identity.auth.application.dto.AuthSession;

public interface GetSessionUseCase {

    AuthSession handle();
}
