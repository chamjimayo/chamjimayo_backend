package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public class AuthTokenResponse {
  private final String accessToken;
  private final String refreshToken;

  private AuthTokenResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static AuthTokenResponse create(String accessToken, String refreshToken) {
    return new AuthTokenResponse(accessToken, refreshToken);
  }
}
