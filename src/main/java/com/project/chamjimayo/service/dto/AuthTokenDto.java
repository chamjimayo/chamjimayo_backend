package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.AuthTokenResponse;
import lombok.Getter;

@Getter
public class AuthTokenDto {
  private final String token;

  private AuthTokenDto(String token) {
    this.token = token;
  }

  public static AuthTokenDto create(String token) {
    return new AuthTokenDto(token);
  }

  public AuthTokenResponse toResponse() {
    return AuthTokenResponse.create(token);
  }
}
