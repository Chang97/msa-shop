package com.msashop.auth.attachment.application.command.port.in;

import com.msashop.auth.attachment.application.command.dto.AtchFileCommand;
import com.msashop.auth.attachment.application.command.dto.AtchFileCommandResult;

public interface UpdateAtchFileUseCase {

    AtchFileCommandResult handle(Long atchFileId, AtchFileCommand command);
}
