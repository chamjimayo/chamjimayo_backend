package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.AuthTokenResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Getter
public class AuthTokenDto {
  private final String accessToken;
  private final String refreshToken;

  private final long accessTokenValidityMs;
  private final long refreshTokenValidityMs;

  private AuthTokenDto(String accessToken, String refreshToken, long accessTokenValidityMs,
      long refreshTokenValidityMs) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenValidityMs = accessTokenValidityMs;
    this.refreshTokenValidityMs = refreshTokenValidityMs;
  }

  public static AuthTokenDto create(String accessToken, String refreshToken,
      long accessTokenValidityMs, long refreshTokenValidityMs) {
    return new AuthTokenDto(accessToken, refreshToken, accessTokenValidityMs,
        refreshTokenValidityMs);
  }

  public AuthTokenResponse toResponse() {
    return AuthTokenResponse.create(accessToken, refreshToken, accessTokenValidityMs,
         refreshTokenValidityMs);
  }
}
