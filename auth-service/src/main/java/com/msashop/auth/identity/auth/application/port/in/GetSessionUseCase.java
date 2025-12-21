package com.msashop.auth.identity.auth.application.port.in;

import com.msashop.auth.identity.auth.application.dto.AuthSession;

public interface GetSessionUseCase {

    AuthSession handle();
}
