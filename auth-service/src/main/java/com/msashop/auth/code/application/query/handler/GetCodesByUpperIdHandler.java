package com.msashop.auth.code.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.code.application.query.dto.CodeQueryResult;
import com.msashop.auth.code.application.query.mapper.CodeQueryMapper;
import com.msashop.auth.code.application.query.port.in.GetCodesByUpperIdUseCase;
import com.msashop.auth.code.domain.port.out.CodeQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCodesByUpperIdHandler implements GetCodesByUpperIdUseCase {

    private final CodeQueryPort codeQueryPort;
    private final CodeQueryMapper codeQueryMapper;

    @Override
    public List<CodeQueryResult> handle(Long upperCodeId) {
        return codeQueryPort.findActiveChildrenByUpperId(upperCodeId).stream()
                .map(codeQueryMapper::toResult)
                .toList();
    }
}
