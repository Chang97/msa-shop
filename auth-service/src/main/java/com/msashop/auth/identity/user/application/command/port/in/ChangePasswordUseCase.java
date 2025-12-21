package com.msashop.auth.identity.user.application.command.port.in;

import com.msashop.auth.identity.user.application.command.dto.UserPasswordCommand;

public interface ChangePasswordUseCase {

    void handle(UserPasswordCommand command);
}
