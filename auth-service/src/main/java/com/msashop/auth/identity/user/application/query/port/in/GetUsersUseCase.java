package com.msashop.auth.identity.user.application.query.port.in;

import java.util.List;

import com.msashop.auth.identity.user.application.query.dto.UserQuery;
import com.msashop.auth.identity.user.application.query.dto.UserQueryResult;

public interface GetUsersUseCase {

    List<UserQueryResult> handle(UserQuery query);
}
