package com.msashop.auth.identity.user.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.identity.user.application.command.port.in.DeleteUserUseCase;
import com.msashop.auth.identity.user.domain.model.User;
import com.msashop.auth.identity.user.domain.port.out.UserCommandPort;
import com.msashop.auth.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteUserHandler implements DeleteUserUseCase {

    private final UserCommandPort userCommandPort;

    @Override
    public void handle(Long userId) {
        User existing = userCommandPort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        existing.disable();
        userCommandPort.save(existing);
    }
}
