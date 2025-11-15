package com.msashop.auth.contexts.attachment.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.msashop.auth.contexts.attachment.domain.model.AtchFile;

public interface AtchFileQueryPort {

    Optional<AtchFile> findById(Long atchFileId);

    List<AtchFile> findAll();
}
