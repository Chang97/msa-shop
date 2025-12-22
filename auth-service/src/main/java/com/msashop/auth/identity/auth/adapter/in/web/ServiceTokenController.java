package com.msashop.auth.identity.auth.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msashop.auth.identity.auth.adapter.in.web.dto.ServiceTokenRequest;
import com.msashop.auth.identity.auth.adapter.in.web.dto.ServiceTokenResponse;
import com.msashop.auth.identity.auth.application.dto.ServiceToken;
import com.msashop.auth.identity.auth.application.port.in.IssueServiceTokenCommand;
import com.msashop.auth.identity.auth.application.port.in.IssueServiceTokenUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/internal/service-tokens")
public class ServiceTokenController {

    private final IssueServiceTokenUseCase issueServiceTokenUseCase;

    @PostMapping
    public ResponseEntity<ServiceTokenResponse> issue(@Valid @RequestBody ServiceTokenRequest request) {
        ServiceToken token = issueServiceTokenUseCase.issue(
                new IssueServiceTokenCommand(request.clientId(), request.clientSecret()));
        ServiceTokenResponse response = new ServiceTokenResponse(token.accessToken(), token.expiresIn());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
