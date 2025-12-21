package com.msashop.auth.authr.menu.application.query.port.in;

import com.msashop.auth.authr.menu.application.query.dto.MenuQueryResult;

public interface GetMenuUseCase {

    MenuQueryResult handle(Long menuId);
}
