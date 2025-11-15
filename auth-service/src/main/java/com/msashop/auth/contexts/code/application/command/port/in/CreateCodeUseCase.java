package com.msashop.auth.contexts.code.application.command.port.in;

import com.msashop.auth.contexts.code.application.command.dto.CodeCommand;
import com.msashop.auth.contexts.code.application.command.dto.CodeCommandResult;

public interface CreateCodeUseCase {

    CodeCommandResult handle(CodeCommand command);
}
