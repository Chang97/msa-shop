package com.msashop.auth.code.application.command.port.in;

public interface DeleteCodeUseCase {

    void handle(Long codeId);
}
