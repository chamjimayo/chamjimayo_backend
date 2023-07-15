package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public class ValidationNicknameResponse {

  private final boolean isNicknameDuplicate;

  public ValidationNicknameResponse(boolean isNicknameDuplicate) {
    this.isNicknameDuplicate = isNicknameDuplicate;
  }

  public static ValidationNicknameResponse create(boolean isNicknameDuplicate) {
    return new ValidationNicknameResponse(isNicknameDuplicate);
  }
}
