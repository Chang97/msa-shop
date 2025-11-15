package com.msashop.auth.contexts.authr.menu.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.msashop.auth.contexts.authr.menu.application.command.dto.MenuCommand;
import com.msashop.auth.contexts.authr.menu.application.command.dto.MenuCommandResult;
import com.msashop.auth.contexts.authr.menu.application.command.mapper.MenuCommandMapper;
import com.msashop.auth.contexts.authr.menu.application.command.port.in.CreateMenuUseCase;
import com.msashop.auth.contexts.authr.menu.application.command.support.MenuPermissionSynchronizer;
import com.msashop.auth.contexts.authr.menu.domain.model.Menu;
import com.msashop.auth.contexts.authr.menu.domain.model.MenuId;
import com.msashop.auth.contexts.authr.menu.domain.policy.MenuPolicy;
import com.msashop.auth.contexts.authr.menu.domain.port.out.MenuCommandPort;
import com.msashop.auth.platform.exception.ConflictException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class CreateMenuHandler implements CreateMenuUseCase {

    private final MenuCommandPort menuRepository;
    private final MenuPermissionSynchronizer menuPermissionSynchronizer;
    private final AuthorityCacheEventPort authorityCacheEventPort;
    private final MenuCommandMapper menuCommandMapper;

    @Override
    public MenuCommandResult handle(MenuCommand command) {
        Menu menu = menuCommandMapper.toDomain(command);
        if (menuRepository.existsByMenuCode(menu.getMenuCode())) {
            throw new ConflictException("Menu code already exists: " + menu.getMenuCode());
        }
        MenuPolicy.using(this::menuExists).assertUpperExists(menu.getUpperMenuId());

        Menu saved = menuRepository.save(menu);
        menuPermissionSynchronizer.sync(saved.getMenuId().value(), command.permissionIds());
        authorityCacheEventPort.publishPermissionsChanged(List.of());
        return menuCommandMapper.toResult(saved);
    }

    private Boolean menuExists(MenuId menuId) {
        return menuId != null && menuRepository.existsById(menuId.value());
    }
}
