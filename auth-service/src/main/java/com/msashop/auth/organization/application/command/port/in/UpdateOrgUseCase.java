package com.msashop.auth.organization.application.command.port.in;

import com.msashop.auth.organization.application.command.dto.OrgCommand;
import com.msashop.auth.organization.application.command.dto.OrgCommandResult;

public interface UpdateOrgUseCase {

    OrgCommandResult handle(Long orgId, OrgCommand command);
}
