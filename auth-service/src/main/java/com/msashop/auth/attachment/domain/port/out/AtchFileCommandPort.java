package com.msashop.auth.attachment.domain.port.out;

import java.util.Optional;

import com.msashop.auth.attachment.domain.model.AtchFile;

public interface AtchFileCommandPort {

    AtchFile save(AtchFile atchFile);

    Optional<AtchFile> findById(Long atchFileId);

    void deleteById(Long atchFileId);
}
