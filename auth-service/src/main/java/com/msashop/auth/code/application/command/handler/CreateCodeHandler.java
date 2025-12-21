package com.msashop.auth.code.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.code.application.command.dto.CodeCommand;
import com.msashop.auth.code.application.command.dto.CodeCommandResult;
import com.msashop.auth.code.application.command.mapper.CodeCommandMapper;
import com.msashop.auth.code.application.command.port.in.CreateCodeUseCase;
import com.msashop.auth.code.domain.model.Code;
import com.msashop.auth.code.domain.model.CodeId;
import com.msashop.auth.code.domain.policy.CodePolicy;
import com.msashop.auth.code.domain.port.out.CodeCommandPort;
import com.msashop.auth.infrastructure.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateCodeHandler implements CreateCodeUseCase {

    private final CodeCommandPort codeCommandPort;
    private final CodeCommandMapper codeCommandMapper;
    private final CodePolicy codePolicy;

    @Override
    public CodeCommandResult handle(CodeCommand command) {
        if (codeCommandPort.existsByCode(command.code())) {
            throw new ConflictException("Code already exists: " + command.code());
        }

        Code code = codeCommandMapper.toDomain(command);
        CodeId upperId = code.getUpperCodeId();
        String parentOrderPath = codePolicy.resolveParentOrderPath(upperId);
        code.attachTo(upperId, parentOrderPath);

        Code saved = codeCommandPort.save(code);
        return codeCommandMapper.toCommandResult(saved);
    }
}
