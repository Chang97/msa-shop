package com.msashop.auth.attachment.adapter.out.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.msashop.auth.attachment.adapter.out.persistence.entity.AtchFileEntity;
import com.msashop.auth.attachment.adapter.out.persistence.entity.AtchFileItemEntity;
import com.msashop.auth.attachment.adapter.out.persistence.mapper.AtchFileItemEntityMapper;
import com.msashop.auth.attachment.adapter.out.persistence.repo.AtchFileItemJpaRepository;
import com.msashop.auth.attachment.adapter.out.persistence.repo.AtchFileJpaRepository;
import com.msashop.auth.attachment.domain.model.AtchFileItem;
import com.msashop.auth.attachment.domain.port.out.AtchFileItemCommandPort;
import com.msashop.auth.attachment.domain.port.out.AtchFileItemQueryPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AtchFileItemRepositoryAdapter implements AtchFileItemCommandPort, AtchFileItemQueryPort {

    private final AtchFileItemJpaRepository atchFileItemJpaRepository;
    private final AtchFileJpaRepository atchFileJpaRepository;
    private final AtchFileItemEntityMapper entityMapper;

    @Override
    public AtchFileItem save(AtchFileItem item) {
        AtchFileItemEntity entity;
        if (item.getAtchFileItemId() == null) {
            Long atchFileId = item.getAtchFileId() != null ? item.getAtchFileId().value() : null;
            if (atchFileId == null) {
                throw new IllegalArgumentException("Attachment file id is required");
            }
            AtchFileEntity parent = atchFileJpaRepository.findById(atchFileId)
                    .orElseThrow(() -> new NotFoundException("Attachment file not found"));
            entity = entityMapper.toNewEntity(item, parent);
        } else {
            entity = atchFileItemJpaRepository.findById(item.getAtchFileItemId().value())
                    .orElseThrow(() -> new NotFoundException("Attachment file item not found"));
            entityMapper.merge(item, entity);
        }
        AtchFileItemEntity saved = atchFileItemJpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public Optional<AtchFileItem> findById(Long atchFileItemId) {
        return atchFileItemJpaRepository.findById(atchFileItemId)
                .map(entityMapper::toDomain);
    }

    @Override
    public List<AtchFileItem> findByAtchFileId(Long atchFileId) {
        return atchFileItemJpaRepository.findByAtchFile_AtchFileId(atchFileId).stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long atchFileItemId) {
        atchFileItemJpaRepository.deleteById(atchFileItemId);
    }

    @Override
    public void deleteByAtchFileId(Long atchFileId) {
        atchFileItemJpaRepository.deleteByAtchFile_AtchFileId(atchFileId);
    }
}
