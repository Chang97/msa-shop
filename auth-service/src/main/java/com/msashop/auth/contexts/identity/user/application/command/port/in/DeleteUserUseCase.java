package com.msashop.auth.contexts.identity.user.application.command.port.in;

public interface DeleteUserUseCase {

    void handle(Long userId);
}
