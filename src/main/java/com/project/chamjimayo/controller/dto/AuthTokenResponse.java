package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public class AuthTokenResponse {
  private final String token;

  public AuthTokenResponse(String token) {
    this.token = token;
  }

  public static AuthTokenResponse create(String token) {
    return new AuthTokenResponse(token);
  }
}
