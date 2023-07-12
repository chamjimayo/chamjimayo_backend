package com.project.chamjimayo.security.dto;

import com.project.chamjimayo.controller.dto.AuthTokenResponse;
import lombok.Getter;

@Getter
public class AuthTokenDto {
  private final String accessToken;
  private final String refreshToken;

  private AuthTokenDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static AuthTokenDto create(String accessToken, String refreshToken) {
    return new AuthTokenDto(accessToken, refreshToken);
  }

  public AuthTokenResponse toResponse() {
    return AuthTokenResponse.create(accessToken, refreshToken);
  }
}
