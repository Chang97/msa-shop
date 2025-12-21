package com.msashop.auth.organization.application.query.port.in;

import com.msashop.auth.organization.application.query.dto.OrgQueryResult;

public interface GetOrgUseCase {

    OrgQueryResult handle(Long orgId);
}
