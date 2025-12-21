package com.msashop.auth.identity.user.application.command.port.in;

import com.msashop.auth.identity.user.application.command.dto.UserCommand;
import com.msashop.auth.identity.user.application.command.dto.UserCommandResult;

public interface CreateUserUseCase {

    UserCommandResult handle(UserCommand command);
}
