package com.project.chamjimayo.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthTokenDto {

  @Schema(description = "access token", example = "jwt token")
  private final String accessToken;

  @Schema(description = "refresh token", example = "jwt token")
  private final String refreshToken;

  private AuthTokenDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public static AuthTokenDto create(String accessToken, String refreshToken) {
    return new AuthTokenDto(accessToken, refreshToken);
  }
}
