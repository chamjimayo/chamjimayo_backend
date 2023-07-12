package com.project.chamjimayo.service.dto;

import lombok.Getter;

@Getter
public class IssueTokenDto {

  private String authId;
  private String refreshToken;

  public IssueTokenDto(String authId, String refreshToken) {
    this.authId = authId;
    this.refreshToken = refreshToken;
  }

  public static IssueTokenDto create(String authId, String refreshToken) {
    return new IssueTokenDto(authId, refreshToken);
  }
}
