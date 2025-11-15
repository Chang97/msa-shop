package com.msashop.auth.contexts.authr.menu.application.command.port.in;

import com.msashop.auth.contexts.authr.menu.application.command.dto.MenuCommand;
import com.msashop.auth.contexts.authr.menu.application.command.dto.MenuCommandResult;

public interface CreateMenuUseCase {

    MenuCommandResult handle(MenuCommand command);
}
