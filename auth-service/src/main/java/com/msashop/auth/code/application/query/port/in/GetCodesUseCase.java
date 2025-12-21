package com.msashop.auth.code.application.query.port.in;

import java.util.List;

import com.msashop.auth.code.application.query.dto.CodeQuery;
import com.msashop.auth.code.application.query.dto.CodeQueryResult;

public interface GetCodesUseCase {

    List<CodeQueryResult> handle(CodeQuery condition);
}
