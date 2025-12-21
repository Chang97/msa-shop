package com.msashop.auth.identity.user.application.command.port.in;

public interface DeleteUserUseCase {

    void handle(Long userId);
}
