package com.msashop.auth.organization.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.infrastructure.exception.NotFoundException;
import com.msashop.auth.organization.application.command.port.in.DeleteOrgUseCase;
import com.msashop.auth.organization.domain.port.out.OrgCommandPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteOrgHandler implements DeleteOrgUseCase {

    private final OrgCommandPort orgCommandPort;

    @Override
    public void handle(Long orgId) {
        boolean exists = orgCommandPort.findById(orgId).isPresent();
        if (!exists) {
            throw new NotFoundException("Org not found. id=" + orgId);
        }
        orgCommandPort.deleteById(orgId);
    }
}
