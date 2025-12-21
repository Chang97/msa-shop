package com.msashop.auth.attachment.application.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommand;
import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommandResult;
import com.msashop.auth.attachment.domain.model.AtchFileId;
import com.msashop.auth.attachment.domain.model.AtchFileItem;

@Component
public class AtchFileItemCommandMapper {

    public AtchFileItem toDomain(AtchFileItemCommand command) {
        AtchFileItem item = AtchFileItem.create(
                AtchFileId.of(command.atchFileId()),
                command.path(),
                command.fileName(),
                command.fileSize()
        );
        item.changeUseYn(command.useYn());
        return item;
    }

    public AtchFileItemCommandResult toResult(AtchFileItem item) {
        Long id = item.getAtchFileItemId() != null ? item.getAtchFileItemId().value() : null;
        return new AtchFileItemCommandResult(
                id,
                item.getAtchFileId() != null ? item.getAtchFileId().value() : null,
                item.getPath(),
                item.getFileName(),
                item.getFileSize(),
                item.getUseYn(),
                item.getCreatedBy(),
                item.getUpdatedBy(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}
