package com.msashop.auth.attachment.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.attachment.application.command.port.in.DeleteAtchFileItemUseCase;
import com.msashop.auth.attachment.domain.port.out.AtchFileItemCommandPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class DeleteAtchFileItemHandler implements DeleteAtchFileItemUseCase {

    private final AtchFileItemCommandPort atchFileItemCommandPort;

    @Override
    public void handle(Long atchFileItemId) {
        atchFileItemCommandPort.deleteById(atchFileItemId);
    }
}
