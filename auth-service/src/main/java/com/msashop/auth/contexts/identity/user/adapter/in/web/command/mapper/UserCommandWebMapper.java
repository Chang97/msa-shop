package com.msashop.auth.contexts.identity.user.adapter.in.web.command.mapper;

import org.springframework.stereotype.Component;

import com.msashop.auth.contexts.identity.user.adapter.in.web.command.dto.UserCommandRequest;
import com.msashop.auth.contexts.identity.user.adapter.in.web.command.dto.UserCommandResponse;
import com.msashop.auth.contexts.identity.user.adapter.in.web.command.dto.UserPasswordRequest;
import com.msashop.auth.contexts.identity.user.application.command.dto.UserCommand;
import com.msashop.auth.contexts.identity.user.application.command.dto.UserCommandResult;
import com.msashop.auth.contexts.identity.user.application.command.dto.UserPasswordCommand;

@Component
public class UserCommandWebMapper {

    public UserCommand toCommand(UserCommandRequest request) {
        return new UserCommand(
                request.email(),
                request.loginId(),
                request.userPassword(),
                request.userName(),
                request.orgId(),
                request.empNo(),
                request.positionName(),
                request.tel(),
                request.statusCodeId(),
                request.useYn(),
                request.roleIds()
        );
    }

    public UserPasswordCommand toPasswordCommand(Long userId, UserPasswordRequest request) {
        return new UserPasswordCommand(userId, request.password());
    }

    public UserCommandResponse toResponse(UserCommandResult result) {
        return new UserCommandResponse(result.userId());
    }
}
