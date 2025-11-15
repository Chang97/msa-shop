package com.msashop.auth.contexts.identity.user.application.command.port.in;

import com.msashop.auth.contexts.identity.user.application.command.dto.UserPasswordCommand;

public interface ChangePasswordUseCase {

    void handle(UserPasswordCommand command);
}
