package com.project.chamjimayo.controller.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "google")
@Component
@Getter
@Setter
public class GoogleProperties {
  private String googleAccountFilePath;
  private String googleApplicationPackageName;
}
