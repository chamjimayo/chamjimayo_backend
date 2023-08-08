package com.project.chamjimayo.controller.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class GoogleCredentialsConfig {

  private final GoogleProperties googleProperties;
  private final ResourceLoader resourceLoader;

  @Bean
  public AndroidPublisher androidPublisher() {
    try {
      Resource resource = resourceLoader.getResource(
          "classpath:".concat(googleProperties.getGoogleAccountFilePath()));

      GoogleCredentials googleCredentials = GoogleCredentials.fromStream(resource.getInputStream());

      return new AndroidPublisher.Builder(GoogleNetHttpTransport.newTrustedTransport(),
          GsonFactory.getDefaultInstance(),
          new HttpCredentialsAdapter(googleCredentials)).setApplicationName(
          googleProperties.getGoogleApplicationPackageName()).build();
    } catch (IOException | GeneralSecurityException e) {
      log.error("failed to create android publisher", e);
      throw new RuntimeException("안드로이드 결제 검증에 실패했습니다.");
    }
  }
}
