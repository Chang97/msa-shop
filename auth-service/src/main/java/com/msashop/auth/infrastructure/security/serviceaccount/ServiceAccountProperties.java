package com.msashop.auth.infrastructure.security.serviceaccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(prefix = "security.service-accounts")
public class ServiceAccountProperties {

    private final List<ServiceAccountConfig> accounts = new ArrayList<>();

    public List<ServiceAccountConfig> getAccounts() {
        return accounts;
    }

    public Optional<ServiceAccountConfig> findByClientId(String clientId) {
        if (!StringUtils.hasText(clientId)) {
            return Optional.empty();
        }
        return accounts.stream()
                .filter(account -> clientId.equals(account.getClientId()))
                .findFirst();
    }

    public static class ServiceAccountConfig {
        private String clientId;
        private String clientSecret;
        private List<String> permissions = new ArrayList<>();

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public List<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<String> permissions) {
            this.permissions = permissions == null ? new ArrayList<>() : new ArrayList<>(permissions);
        }
    }
}
