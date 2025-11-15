package com.msashop.auth.contexts.identity.user.application.command.dto;

public record UserPasswordCommand(Long userId, String rawPassword) {
}
