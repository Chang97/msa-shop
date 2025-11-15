package com.msashop.auth.contexts.attachment.application.command.port.in;

import com.msashop.auth.contexts.attachment.application.command.dto.AtchFileItemCommand;
import com.msashop.auth.contexts.attachment.application.command.dto.AtchFileItemCommandResult;

public interface CreateAtchFileItemUseCase {

    AtchFileItemCommandResult handle(AtchFileItemCommand command);
}
