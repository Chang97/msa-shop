package com.msashop.auth.identity.user.application.command.dto;

public record UserPasswordCommand(Long userId, String rawPassword) {
}
