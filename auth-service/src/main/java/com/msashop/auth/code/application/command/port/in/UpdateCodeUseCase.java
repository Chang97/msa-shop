package com.msashop.auth.code.application.command.port.in;

import com.msashop.auth.code.application.command.dto.CodeCommand;
import com.msashop.auth.code.application.command.dto.CodeCommandResult;

public interface UpdateCodeUseCase {

    CodeCommandResult handle(Long codeId, CodeCommand command);
}
