package com.msashop.auth.attachment.application.query.port.in;

import java.util.List;

import com.msashop.auth.attachment.application.query.dto.AtchFileItemResult;

public interface GetAtchFileItemsUseCase {

    List<AtchFileItemResult> handle(Long atchFileId);
}
