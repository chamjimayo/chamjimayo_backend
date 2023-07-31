package com.project.chamjimayo.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "api")
@Component
@Getter
@Setter
public class ApiProperties {

	private String apiKey;

	private String headerName;
}
