package com.msashop.auth.contexts.identity.user.application.query.port.in;

import com.msashop.auth.contexts.identity.user.application.query.dto.UserQueryResult;

public interface GetUserUseCase {

    UserQueryResult handle(Long userId);
}
