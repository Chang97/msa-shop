package com.msashop.auth.organization.application.command.port.in;

import com.msashop.auth.organization.application.command.dto.OrgCommand;
import com.msashop.auth.organization.application.command.dto.OrgCommandResult;

public interface CreateOrgUseCase {

    OrgCommandResult handle(OrgCommand command);
}
