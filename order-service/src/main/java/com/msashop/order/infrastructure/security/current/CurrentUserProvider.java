package com.msashop.order.infrastructure.security.current;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public String userIdOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication != null ? authentication.getPrincipal() : null;
        return principal != null ? principal.toString() : null;
    }

    public String permissionsHeader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "";
        }
        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities() == null ? Collections.emptyList() : authentication.getAuthorities();

        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(org.springframework.util.StringUtils::hasText)
                .collect(Collectors.joining(","));
    }
}
