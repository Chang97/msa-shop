package com.msashop.auth.attachment.domain.port.out;

import java.util.Optional;

import com.msashop.auth.attachment.domain.model.AtchFileItem;

public interface AtchFileItemCommandPort {

    AtchFileItem save(AtchFileItem item);

    Optional<AtchFileItem> findById(Long atchFileItemId);

    void deleteById(Long atchFileItemId);

    void deleteByAtchFileId(Long atchFileId);
}
