package com.msashop.auth.organization.application.query.port.in;

import java.util.List;

import com.msashop.auth.organization.application.query.dto.OrgQuery;
import com.msashop.auth.organization.application.query.dto.OrgQueryResult;

public interface GetOrgsUseCase {

    List<OrgQueryResult> handle(OrgQuery query);
}
