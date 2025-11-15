package com.msashop.auth.contexts.organization.application.query.port.in;

import com.msashop.auth.contexts.organization.application.query.dto.OrgQueryResult;

public interface GetOrgUseCase {

    OrgQueryResult handle(Long orgId);
}
