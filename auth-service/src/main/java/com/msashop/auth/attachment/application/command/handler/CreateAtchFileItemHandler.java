package com.msashop.auth.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommand;
import com.msashop.auth.attachment.application.command.dto.AtchFileItemCommandResult;
import com.msashop.auth.attachment.application.command.mapper.AtchFileItemCommandMapper;
import com.msashop.auth.attachment.application.command.port.in.CreateAtchFileItemUseCase;
import com.msashop.auth.attachment.domain.model.AtchFileItem;
import com.msashop.auth.attachment.domain.port.out.AtchFileCommandPort;
import com.msashop.auth.attachment.domain.port.out.AtchFileItemCommandPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class CreateAtchFileItemHandler implements CreateAtchFileItemUseCase {

    private final AtchFileCommandPort atchFileCommandPort;
    private final AtchFileItemCommandPort atchFileItemCommandPort;
    private final AtchFileItemCommandMapper commandMapper;

    @Override
    public AtchFileItemCommandResult handle(AtchFileItemCommand command) {
        Long atchFileId = command.atchFileId();
        atchFileCommandPort.findById(atchFileId)
                .orElseThrow(() -> new NotFoundException("Attachment file not found. id=" + atchFileId));

        AtchFileItem item = commandMapper.toDomain(command);
        AtchFileItem saved = atchFileItemCommandPort.save(item);
        return commandMapper.toResult(saved);
    }
}
