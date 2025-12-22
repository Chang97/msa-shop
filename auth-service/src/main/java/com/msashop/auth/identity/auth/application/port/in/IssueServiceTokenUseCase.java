package com.msashop.auth.identity.auth.application.port.in;

import com.msashop.auth.identity.auth.application.dto.ServiceToken;

public interface IssueServiceTokenUseCase {

    ServiceToken issue(IssueServiceTokenCommand command);
}
