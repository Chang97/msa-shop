package com.msashop.auth.contexts.identity.user.application.query.port.in;

import com.msashop.auth.contexts.identity.user.application.query.dto.LoginIdAvailabilityResult;

public interface CheckLoginIdUseCase {

    LoginIdAvailabilityResult handle(String loginId);
}
