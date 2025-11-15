package com.msashop.auth.contexts.attachment.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.auth.contexts.attachment.adapter.in.web.command.dto.AtchFileItemCommandRequest;
import com.msashop.auth.contexts.attachment.adapter.in.web.command.dto.AtchFileItemCommandResponse;
import com.msashop.auth.contexts.attachment.application.command.dto.AtchFileItemCommand;
import com.msashop.auth.contexts.attachment.application.command.dto.AtchFileItemCommandResult;

@Component
public class AtchFileItemCommandWebMapper {

    public AtchFileItemCommand toCommand(Long atchFileId, AtchFileItemCommandRequest request) {
        return new AtchFileItemCommand(
                atchFileId,
                request.path(),
                request.fileName(),
                request.fileSize(),
                request.useYn()
        );
    }

    public AtchFileItemCommandResponse toResponse(AtchFileItemCommandResult result) {
        return new AtchFileItemCommandResponse(
                result.atchFileItemId(),
                result.atchFileId(),
                result.path(),
                result.fileName(),
                result.fileSize(),
                result.useYn(),
                result.createdBy(),
                result.updatedBy(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
