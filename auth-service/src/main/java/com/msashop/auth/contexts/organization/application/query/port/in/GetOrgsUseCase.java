package com.msashop.auth.contexts.organization.application.query.port.in;

import java.util.List;

import com.msashop.auth.contexts.organization.application.query.dto.OrgQuery;
import com.msashop.auth.contexts.organization.application.query.dto.OrgQueryResult;

public interface GetOrgsUseCase {

    List<OrgQueryResult> handle(OrgQuery query);
}
