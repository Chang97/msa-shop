package com.msashop.auth.organization.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.infrastructure.exception.ConflictException;
import com.msashop.auth.infrastructure.exception.NotFoundException;
import com.msashop.auth.organization.application.command.dto.OrgCommand;
import com.msashop.auth.organization.application.command.dto.OrgCommandResult;
import com.msashop.auth.organization.application.command.mapper.OrgCommandMapper;
import com.msashop.auth.organization.application.command.port.in.UpdateOrgUseCase;
import com.msashop.auth.organization.domain.model.Org;
import com.msashop.auth.organization.domain.model.OrgId;
import com.msashop.auth.organization.domain.policy.OrgPolicy;
import com.msashop.auth.organization.domain.port.out.OrgCommandPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateOrgHandler implements UpdateOrgUseCase {

    private final OrgCommandPort orgCommandPort;
    private final OrgCommandMapper commandMapper;

    @Override
    public OrgCommandResult handle(Long orgId, OrgCommand command) {
        Org existing = orgCommandPort.findById(orgId)
                .orElseThrow(() -> new NotFoundException("Org not found. id=" + orgId));

        if (!existing.getOrgCode().equals(command.orgCode())
                && orgCommandPort.existsByOrgCode(command.orgCode())) {
            throw new ConflictException("Org code already exists: " + command.orgCode());
        }

        OrgId newUpper = OrgId.of(command.upperOrgId());
        OrgPolicy.using(this::orgExists).assertUpperExists(newUpper);

        existing.changeOrgCode(command.orgCode());
        existing.changeOrgName(command.orgName());
        existing.changeSrt(command.srt());
        existing.changeUseYn(command.useYn());
        existing.attachTo(newUpper);

        Org saved = orgCommandPort.save(existing);
        return commandMapper.toResult(saved);
    }

    private Boolean orgExists(OrgId orgId) {
        return orgId != null && orgCommandPort.existsById(orgId.value());
    }
}
