package com.msashop.auth.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.attachment.application.command.port.in.DeleteAtchFileUseCase;
import com.msashop.auth.attachment.domain.port.out.AtchFileCommandPort;
import com.msashop.auth.attachment.domain.port.out.AtchFileItemCommandPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteAtchFileHandler implements DeleteAtchFileUseCase {

    private final AtchFileCommandPort atchFileCommandPort;
    private final AtchFileItemCommandPort atchFileItemCommandPort;

    @Override
    public void handle(Long atchFileId) {
        atchFileItemCommandPort.deleteByAtchFileId(atchFileId);
        atchFileCommandPort.deleteById(atchFileId);
    }
}
