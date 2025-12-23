package com.msashop.auth.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.internal")
public class InternalAccessProperties {

    /**
     * 내부 호출에서 사용할 헤더 이름 (기본: X-Internal-Secret)
     */
    private String headerName = "X-Internal-Secret";

    /**
     * order-service 등 내부 서비스가 공유하는 시크릿 값.
     */
    private String serviceSecret;

}
