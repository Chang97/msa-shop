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
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String ACCESS_TOKEN_COOKIE = "ACCESS_TOKEN";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = resolveToken(exchange);
        if (token == null) {
            log.debug("JWT token not found in Authorization header or {} cookie.", ACCESS_TOKEN_COOKIE);
            return unauthorized(exchange);
        }
        try {
            Claims claims = jwtTokenProvider.validate(token);
            String userId = extractUserId(claims);
            String roles = claims.get("roles", String.class);
            ServerHttpRequest mutated = exchange.getRequest()
                    .mutate()
                    .headers(h -> {
                        h.add("X-User-Id", userId);
                        if (roles != null) {
                            h.add("X-User-Roles", roles);
                        }
                    })
                    .build();
            return chain.filter(exchange.mutate().request(mutated).build());
        } catch (JwtException ex) {
            log.warn("JWT validation failed: {}", ex.getMessage());
            return unauthorized(exchange);
        }
    }

    private String resolveToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        var cookie = exchange.getRequest().getCookies().getFirst(ACCESS_TOKEN_COOKIE);
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
}
