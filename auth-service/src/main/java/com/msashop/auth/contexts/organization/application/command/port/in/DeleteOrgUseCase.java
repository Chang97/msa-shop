package com.msashop.auth.contexts.organization.application.command.port.in;

public interface DeleteOrgUseCase {

    void handle(Long orgId);
}
