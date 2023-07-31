package com.project.chamjimayo.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Component
@Getter
@Setter
public class JwtProperties {

  private String secretKey;

  private long accessTokenValidityMs;

  private long refreshTokenValidityMs;
}
