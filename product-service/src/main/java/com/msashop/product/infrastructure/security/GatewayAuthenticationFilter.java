package com.msashop.product.infrastructure.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GatewayAuthenticationFilter extends OncePerRequestFilter {

    private static final String PERMISSIONS_HEADER = "X-User-Permissions";
    private static final String USER_ID_HEADER = "X-User-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            Authentication authentication = buildAuthentication(request);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private Authentication buildAuthentication(HttpServletRequest request) {
        String userId = request.getHeader(USER_ID_HEADER);
        if (!StringUtils.hasText(userId)) {
            return null;
        }
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(
                request.getHeader(PERMISSIONS_HEADER));
        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(String headerValue) {
        if (!StringUtils.hasText(headerValue)) {
            return Collections.emptyList();
        }
        return Arrays.stream(headerValue.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
