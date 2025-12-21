package com.msashop.auth.code.application.query.port.in;

import java.util.List;

import com.msashop.auth.code.application.query.dto.CodeQueryResult;

public interface GetCodesByUpperIdUseCase {

    List<CodeQueryResult> handle(Long upperCodeId);
}
