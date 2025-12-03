package com.msashop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.msashop.gateway.platform.security.jwt.SecurityProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
