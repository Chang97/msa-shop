package com.msashop.auth.contexts.authr.menu.application.command.handler;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msashop.auth.contexts.authr.cache.domain.port.out.AuthorityCacheEventPort;
import com.msashop.auth.contexts.authr.menu.application.command.port.in.DeleteMenuUseCase;
import com.msashop.auth.contexts.authr.menu.domain.model.Menu;
import com.msashop.auth.contexts.authr.menu.domain.port.out.MenuPermissionMapCommandPort;
import com.msashop.auth.contexts.authr.menu.domain.port.out.MenuCommandPort;
import com.msashop.auth.platform.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
class DeleteMenuHandler implements DeleteMenuUseCase {

    private final MenuCommandPort menuRepository;
    private final MenuPermissionMapCommandPort menuPermissionCommandPort;
    private final AuthorityCacheEventPort authorityCacheEventPort;

    @Override
    public void handle(Long menuId) {
        Menu existing = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException("Menu not found"));

        existing.disable();
        menuRepository.save(existing);
        menuPermissionCommandPort.replacePermissions(menuId, List.of());
        authorityCacheEventPort.publishPermissionsChanged(List.of());
    }
}
