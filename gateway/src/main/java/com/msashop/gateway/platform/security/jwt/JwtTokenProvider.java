package com.msashop.gateway.platform.security.jwt;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final SecretKey secretKey;

    public JwtTokenProvider(SecurityProperties securityProperties) {
        this.secretKey = Keys.hmacShaKeyFor(securityProperties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8));
        log.info("Gateway JWT secret hash={}",
                org.springframework.util.DigestUtils.md5DigestAsHex(
                        securityProperties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8)));
    }

    public Claims validate(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
