package com.msashop.auth.contexts.identity.user.application.command.port.in;

import com.msashop.auth.contexts.identity.user.application.command.dto.UserCommand;
import com.msashop.auth.contexts.identity.user.application.command.dto.UserCommandResult;

public interface UpdateUserUseCase {

    UserCommandResult handle(Long userId, UserCommand command);
}
