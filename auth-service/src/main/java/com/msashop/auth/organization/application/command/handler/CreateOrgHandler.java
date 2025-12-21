package com.msashop.auth.organization.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.infrastructure.exception.ConflictException;
import com.msashop.auth.organization.application.command.dto.OrgCommand;
import com.msashop.auth.organization.application.command.dto.OrgCommandResult;
import com.msashop.auth.organization.application.command.mapper.OrgCommandMapper;
import com.msashop.auth.organization.application.command.port.in.CreateOrgUseCase;
import com.msashop.auth.organization.domain.model.Org;
import com.msashop.auth.organization.domain.model.OrgId;
import com.msashop.auth.organization.domain.policy.OrgPolicy;
import com.msashop.auth.organization.domain.port.out.OrgCommandPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class CreateOrgHandler implements CreateOrgUseCase {

    private final OrgCommandPort orgCommandPort;
    private final OrgCommandMapper commandMapper;

    @Override
    public OrgCommandResult handle(OrgCommand command) {
        if (orgCommandPort.existsByOrgCode(command.orgCode())) {
            throw new ConflictException("Org code already exists: " + command.orgCode());
        }

        Org org = commandMapper.toDomain(command);
        OrgPolicy.using(this::orgExists).assertUpperExists(org.getUpperOrgId());
        Org saved = orgCommandPort.save(org);
        return commandMapper.toResult(saved);
    }

    private Boolean orgExists(OrgId orgId) {
        return orgId != null && orgCommandPort.existsById(orgId.value());
    }
}
