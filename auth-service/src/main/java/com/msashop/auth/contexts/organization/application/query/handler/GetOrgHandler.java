package com.msashop.auth.contexts.organization.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.contexts.organization.application.query.dto.OrgQueryResult;
import com.msashop.auth.contexts.organization.application.query.mapper.OrgQueryMapper;
import com.msashop.auth.contexts.organization.application.query.port.in.GetOrgUseCase;
import com.msashop.auth.contexts.organization.domain.model.Org;
import com.msashop.auth.contexts.organization.domain.port.out.OrgQueryPort;
import com.msashop.auth.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetOrgHandler implements GetOrgUseCase {

    private final OrgQueryPort orgQueryPort;
    private final OrgQueryMapper queryMapper;

    @Override
    public OrgQueryResult handle(Long orgId) {
        Org org = orgQueryPort.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Org not found. id=" + orgId));
        return queryMapper.toResult(org);
    }
}
