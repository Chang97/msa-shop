package com.msashop.auth.contexts.authr.menu.application.query.port.in;

import com.msashop.auth.contexts.authr.menu.application.query.dto.UserMenuAccessResult;

public interface GetAccessibleMenusUseCase {
    UserMenuAccessResult handle(Long userId);
}
