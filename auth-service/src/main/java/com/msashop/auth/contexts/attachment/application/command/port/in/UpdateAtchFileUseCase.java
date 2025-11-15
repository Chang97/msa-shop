package com.msashop.auth.contexts.attachment.application.command.port.in;

import com.msashop.auth.contexts.attachment.application.command.dto.AtchFileCommand;
import com.msashop.auth.contexts.attachment.application.command.dto.AtchFileCommandResult;

public interface UpdateAtchFileUseCase {

    AtchFileCommandResult handle(Long atchFileId, AtchFileCommand command);
}
