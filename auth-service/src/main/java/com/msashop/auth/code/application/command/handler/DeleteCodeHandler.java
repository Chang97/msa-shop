package com.msashop.auth.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.code.application.command.port.in.DeleteCodeUseCase;
import com.msashop.auth.code.domain.model.Code;
import com.msashop.auth.code.domain.port.out.CodeCommandPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteCodeHandler implements DeleteCodeUseCase {

    private final CodeCommandPort codeCommandPort;

    @Override
    public void handle(Long codeId) {
        Code existing = codeCommandPort.findById(codeId)
                .orElseThrow(() -> new NotFoundException("Code not found"));
        existing.disable();
        codeCommandPort.save(existing);
    }
}
