package com.msashop.auth.code.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.code.application.query.dto.CodeQueryResult;
import com.msashop.auth.code.application.query.mapper.CodeQueryMapper;
import com.msashop.auth.code.application.query.port.in.GetCodeUseCase;
import com.msashop.auth.code.domain.port.out.CodeQueryPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodeHandler implements GetCodeUseCase {

    private final CodeQueryPort codeQueryPort;
    private final CodeQueryMapper codeQueryMapper;

    @Override
    public CodeQueryResult handle(Long codeId) {
        return codeQueryPort.findById(codeId)
                .map(codeQueryMapper::toResult)
                .orElseThrow(() -> new NotFoundException("Code not found"));
    }
}
