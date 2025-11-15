package com.msashop.auth.contexts.organization.application.command.port.in;

import com.msashop.auth.contexts.organization.application.command.dto.OrgCommand;
import com.msashop.auth.contexts.organization.application.command.dto.OrgCommandResult;

public interface UpdateOrgUseCase {

    OrgCommandResult handle(Long orgId, OrgCommand command);
}
