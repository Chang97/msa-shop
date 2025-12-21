package com.msashop.auth.attachment.application.command.port.in;

import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommand;
import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommandResult;

public interface CreateAtchFileItemUseCase {

    AtchFileItemCommandResult handle(AtchFileItemCommand command);
}
