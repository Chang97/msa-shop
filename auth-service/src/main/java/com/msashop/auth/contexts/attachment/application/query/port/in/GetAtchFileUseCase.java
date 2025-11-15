package com.msashop.auth.contexts.attachment.application.query.port.in;

import com.msashop.auth.contexts.attachment.application.query.dto.AtchFileQueryResult;

public interface GetAtchFileUseCase {

    AtchFileQueryResult handle(Long atchFileId);
}
