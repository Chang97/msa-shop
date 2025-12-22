package com.msashop.auth.identity.auth.application.support;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.msashop.auth.identity.auth.domain.model.ServiceAccount;
import com.msashop.auth.infrastructure.exception.ValidationException;
import com.msashop.auth.infrastructure.security.serviceaccount.ServiceAccountProperties;

@Component
public class ServiceAccountRegistry {

    private final Map<String, ServiceAccount> accounts;

    public ServiceAccountRegistry(ServiceAccountProperties properties) {
        this.accounts = Collections.unmodifiableMap(
                properties.getAccounts().stream()
                        .filter(config -> StringUtils.hasText(config.getClientId()))
                        .collect(Collectors.toMap(
                                ServiceAccountProperties.ServiceAccountConfig::getClientId,
                                config -> new ServiceAccount(
                                        config.getClientId(),
                                        config.getClientSecret(),
                                        List.copyOf(config.getPermissions())),
                                (left, right) -> left,
                                LinkedHashMap::new
                        ))
        );
    }

    public ServiceAccount authenticate(String clientId, String clientSecret) {
        ServiceAccount account = accounts.get(clientId);
        if (account == null || !Objects.equals(account.clientSecret(), clientSecret)) {
            throw new ValidationException("Invalid service client credentials.");
        }
        return account;
    }
}
