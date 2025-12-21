package com.msashop.auth.identity.user.application.query.port.in;

import com.msashop.auth.identity.user.application.query.dto.LoginIdAvailabilityResult;

public interface CheckLoginIdUseCase {

    LoginIdAvailabilityResult handle(String loginId);
}
