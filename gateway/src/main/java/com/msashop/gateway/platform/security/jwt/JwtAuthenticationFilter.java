package com.msashop.gateway.platform.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String ACCESS_TOKEN_COOKIE = "ACCESS_TOKEN";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_PERMISSIONS_HEADER = "X-User-Permissions";
    private static final String USER_ROLES_HEADER = "X-User-Roles";
    private static final String SERVICE_ID_HEADER = "X-Service-Id";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = resolveToken(request);
        if (token == null) {
            log.debug("JWT token not found in Authorization header or {} cookie.", ACCESS_TOKEN_COOKIE);
            return unauthorized(exchange);
        }
        try {
            Claims claims = jwtTokenProvider.validate(token);
            boolean serviceToken = isServiceToken(claims);
            ServerHttpRequest mutated = request
                    .mutate()
                    .headers(h -> {
                        if (serviceToken) {
                            applyServiceHeaders(request, h, claims);
                        } else {
                            applyUserHeadersFromClaims(h, claims);
                        }
                    })
                    .build();
            return chain.filter(exchange.mutate().request(mutated).build());
        } catch (JwtException ex) {
            log.warn("JWT validation failed: {}", ex.getMessage());
            return unauthorized(exchange);
        }
    }

    private String resolveToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        var cookie = request.getCookies().getFirst(ACCESS_TOKEN_COOKIE);
        return cookie != null ? cookie.getValue() : null;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String extractUserId(Claims claims) {
        Long uid = claims.get("uid", Long.class);
        if (uid != null) {
            return String.valueOf(uid);
        }
        return claims.get("sub", String.class);
    }

    private boolean isServiceToken(Claims claims) {
        Boolean svc = claims.get("svc", Boolean.class);
        return Boolean.TRUE.equals(svc);
    }

    private void applyServiceHeaders(ServerHttpRequest request, HttpHeaders headers, Claims claims) {
        String serviceId = claims.get("cid", String.class);
        if (StringUtils.hasText(serviceId)) {
            headers.set(SERVICE_ID_HEADER, serviceId);
        }
        HttpHeaders original = request.getHeaders();
        applyDelegatedUserHeaders(original, headers, claims);
    }

    private void applyDelegatedUserHeaders(HttpHeaders source, HttpHeaders target, Claims claims) {
        String delegatedUserId = source.getFirst(USER_ID_HEADER);
        if (StringUtils.hasText(delegatedUserId)) {
            target.set(USER_ID_HEADER, delegatedUserId);
        } else {
            applyUserIdFromClaims(target, claims);
        }

        String delegatedPermissions = source.getFirst(USER_PERMISSIONS_HEADER);
        if (StringUtils.hasText(delegatedPermissions)) {
            target.set(USER_PERMISSIONS_HEADER, delegatedPermissions);
        } else {
            applyPermissionsFromClaims(target, claims);
        }

        applyRolesFromClaims(target, claims);
    }

    private void applyUserHeadersFromClaims(HttpHeaders headers, Claims claims) {
        applyUserIdFromClaims(headers, claims);
        applyRolesFromClaims(headers, claims);
        applyPermissionsFromClaims(headers, claims);
    }

    private void applyUserIdFromClaims(HttpHeaders headers, Claims claims) {
        String userId = extractUserId(claims);
        if (StringUtils.hasText(userId)) {
            headers.set(USER_ID_HEADER, userId);
        }
    }

    private void applyPermissionsFromClaims(HttpHeaders headers, Claims claims) {
        List<?> permissions = claims.get("perms", List.class);
        if (permissions == null || permissions.isEmpty()) {
            return;
        }
        String joined = permissions.stream()
                .map(String::valueOf)
                .filter(StringUtils::hasText)
                .collect(Collectors.joining(","));
        if (StringUtils.hasText(joined)) {
            headers.set(USER_PERMISSIONS_HEADER, joined);
        }
    }

    private void applyRolesFromClaims(HttpHeaders headers, Claims claims) {
        String roles = claims.get("roles", String.class);
        if (StringUtils.hasText(roles)) {
            headers.set(USER_ROLES_HEADER, roles);
        }
    }
}
