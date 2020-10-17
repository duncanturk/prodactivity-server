package com.twoyang.prodactivity.server.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "jwt")
@Validated
@Data
public class JWTConfig {
    static final String TOKEN_PREFIX = "Bearer ";
    @NotEmpty
    private String secret;

    @Min(1)
    private long ttl;
}
