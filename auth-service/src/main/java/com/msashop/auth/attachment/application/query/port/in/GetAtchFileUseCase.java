package com.msashop.auth.attachment.application.query.port.in;

import com.msashop.auth.attachment.application.query.dto.AtchFileQueryResult;

public interface GetAtchFileUseCase {

    AtchFileQueryResult handle(Long atchFileId);
}
