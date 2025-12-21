package com.msashop.auth.identity.user.application.query.port.in;

import com.msashop.auth.identity.user.application.query.dto.UserQueryResult;

public interface GetUserUseCase {

    UserQueryResult handle(Long userId);
}
