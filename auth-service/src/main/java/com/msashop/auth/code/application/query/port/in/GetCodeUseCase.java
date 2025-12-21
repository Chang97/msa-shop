package com.msashop.auth.code.application.query.port.in;

import com.msashop.auth.code.application.query.dto.CodeQueryResult;

public interface GetCodeUseCase {

    CodeQueryResult handle(Long codeId);
}
