package com.msashop.gateway.platform.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private final Jwt jwt = new Jwt();

    public static class Jwt {
        private String secret;
        private String issuer;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }
    }

    public Jwt getJwt() {
        return jwt;
    }
}
