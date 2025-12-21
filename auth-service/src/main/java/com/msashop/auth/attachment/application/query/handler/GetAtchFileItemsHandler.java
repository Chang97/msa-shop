package com.msashop.auth.attachment.application.query.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.attachment.application.query.dto.AtchFileItemResult;
import com.msashop.auth.attachment.application.query.mapper.AtchFileQueryMapper;
import com.msashop.auth.attachment.application.query.port.in.GetAtchFileItemsUseCase;
import com.msashop.auth.attachment.domain.port.out.AtchFileItemQueryPort;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetAtchFileItemsHandler implements GetAtchFileItemsUseCase {

    private final AtchFileItemQueryPort atchFileItemQueryPort;
    private final AtchFileQueryMapper queryMapper;

    @Override
    public List<AtchFileItemResult> handle(Long atchFileId) {
        return atchFileItemQueryPort.findByAtchFileId(atchFileId).stream()
                .map(queryMapper::toItemResult)
                .toList();
    }
}
