package com.msashop.auth.contexts.code.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.contexts.code.application.query.dto.CodeQueryResult;
import com.msashop.auth.contexts.code.application.query.mapper.CodeQueryMapper;
import com.msashop.auth.contexts.code.application.query.port.in.GetCodesByUpperCodeUseCase;
import com.msashop.auth.contexts.code.domain.port.out.CodeQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodesByUpperCodeHandler implements GetCodesByUpperCodeUseCase {

    private final CodeQueryPort codeQueryPort;
    private final CodeQueryMapper codeQueryMapper;

    @Override
    public List<CodeQueryResult> handle(String upperCode) {
        return codeQueryPort.findActiveChildrenByUpperCode(upperCode)
                .stream()
                .map(codeQueryMapper::toResult)
                .toList();
    }
}
