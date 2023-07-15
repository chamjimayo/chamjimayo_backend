package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthTokenResponse {

  @Schema(description = "access token")
  private final String accessToken;

  @Schema(description = "refresh token")
  private final String refreshToken;

  private AuthTokenResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static AuthTokenResponse create(String accessToken, String refreshToken) {
    return new AuthTokenResponse(accessToken, refreshToken);
  }
}
