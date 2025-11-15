package com.msashop.auth.contexts.code.application.query.port.in;

import com.msashop.auth.contexts.code.application.query.dto.CodeQueryResult;

public interface GetCodeUseCase {

    CodeQueryResult handle(Long codeId);
}
