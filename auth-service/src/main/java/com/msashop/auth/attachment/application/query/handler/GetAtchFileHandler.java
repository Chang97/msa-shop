package com.msashop.auth.attachment.application.query.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.attachment.application.query.dto.AtchFileQueryResult;
import com.msashop.auth.attachment.application.query.mapper.AtchFileQueryMapper;
import com.msashop.auth.attachment.application.query.port.in.GetAtchFileUseCase;
import com.msashop.auth.attachment.domain.model.AtchFile;
import com.msashop.auth.attachment.domain.port.out.AtchFileItemQueryPort;
import com.msashop.auth.attachment.domain.port.out.AtchFileQueryPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetAtchFileHandler implements GetAtchFileUseCase {

    private final AtchFileQueryPort atchFileQueryPort;
    private final AtchFileItemQueryPort atchFileItemQueryPort;
    private final AtchFileQueryMapper queryMapper;

    @Override
    public AtchFileQueryResult handle(Long atchFileId) {
        AtchFile atchFile = atchFileQueryPort.findById(atchFileId)
                .orElseThrow(() -> new NotFoundException("Attachment file not found. id=" + atchFileId));
        return queryMapper.toResult(atchFile, atchFileItemQueryPort.findByAtchFileId(atchFileId));
    }
}
