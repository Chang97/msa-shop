package com.msashop.auth.contexts.identity.user.application.command.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.contexts.identity.user.application.command.dto.UserCommand;
import com.msashop.auth.contexts.identity.user.application.command.dto.UserCommandResult;
import com.msashop.auth.contexts.identity.user.application.command.mapper.UserCommandMapper;
import com.msashop.auth.contexts.identity.user.application.command.port.in.UpdateUserUseCase;
import com.msashop.auth.contexts.identity.user.domain.model.User;
import com.msashop.auth.contexts.identity.user.domain.port.out.UserCommandPort;
import com.msashop.auth.contexts.identity.user.domain.port.out.UserRoleAssignmentPort;
import com.msashop.auth.platform.exception.ConflictException;
import com.msashop.auth.platform.exception.NotFoundException;
import com.msashop.auth.shared.core.util.StringNormalizer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class UpdateUserHandler implements UpdateUserUseCase {

    private final UserCommandPort userCommandPort;
    private final UserCommandMapper userCommandMapper;
    private final UserRoleAssignmentPort userRoleAssignmentPort;

    @Override
    public UserCommandResult handle(Long userId, UserCommand command) {
        User existing = userCommandPort.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        validateUniqueness(userId, command);
        userCommandMapper.apply(existing, command);
        User saved = userCommandPort.save(existing);
        // 사용자 역할 등록/삭제
        userRoleAssignmentPort.replaceUserRoles(saved.getUserId().value(), command.roleIds());
        return new UserCommandResult(saved.getUserId().value());
    }

    private void validateUniqueness(Long userId, UserCommand command) {
        String email = StringNormalizer.trimToNull(command.email());
        if (email != null && userCommandPort.existsByEmailExcludingId(email, userId)) {
            throw new ConflictException("이미 사용 중인 이메일입니다.");
        }
        String loginId = StringNormalizer.trimToNull(command.loginId());
        if (loginId != null && userCommandPort.existsByLoginIdExcludingId(loginId, userId)) {
            throw new ConflictException("이미 사용 중인 로그인 ID입니다.");
        }
    }


}
