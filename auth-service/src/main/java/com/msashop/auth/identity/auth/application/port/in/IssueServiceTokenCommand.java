package com.msashop.auth.identity.auth.application.port.in;

public record IssueServiceTokenCommand(String clientId, String clientSecret) {
}
