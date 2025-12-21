package com.msashop.auth.organization.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.infrastructure.exception.NotFoundException;
import com.msashop.auth.organization.application.query.dto.OrgQueryResult;
import com.msashop.auth.organization.application.query.mapper.OrgQueryMapper;
import com.msashop.auth.organization.application.query.port.in.GetOrgUseCase;
import com.msashop.auth.organization.domain.model.Org;
import com.msashop.auth.organization.domain.port.out.OrgQueryPort;

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
