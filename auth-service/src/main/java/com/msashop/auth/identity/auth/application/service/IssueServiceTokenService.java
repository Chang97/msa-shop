package com.msashop.auth.identity.auth.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.identity.auth.application.dto.ServiceToken;
import com.msashop.auth.identity.auth.application.port.in.IssueServiceTokenCommand;
import com.msashop.auth.identity.auth.application.port.in.IssueServiceTokenUseCase;
import com.msashop.auth.identity.auth.application.support.ServiceAccountRegistry;
import com.msashop.auth.identity.auth.domain.model.ServiceAccount;
import com.msashop.auth.infrastructure.security.jwt.JwtProperties;
import com.msashop.auth.infrastructure.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueServiceTokenService implements IssueServiceTokenUseCase {

    private final ServiceAccountRegistry serviceAccountRegistry;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    public ServiceToken issue(IssueServiceTokenCommand command) {
        ServiceAccount account = serviceAccountRegistry.authenticate(command.clientId(), command.clientSecret());
        String tokenValue = jwtService.generateServiceToken(account);
        return new ServiceToken(tokenValue, jwtProperties.accessTokenExpirationSeconds());
    }
}
