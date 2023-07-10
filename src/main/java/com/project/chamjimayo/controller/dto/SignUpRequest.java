package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.AuthType;
import com.project.chamjimayo.service.dto.SignUpDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignUpRequest {
  private AuthType authType;
  private String authId;
  private String name;
  private String nickname;
  private String gender;

  public AuthType getAuthType() {
    return authType;
  }

  public String getAuthId() {
    return authId;
  }

  public String getName() {
    return name;
  }

  public String getNickname() {
    return nickname;
  }

  public String getGender() {
    return gender;
  }

  public SignUpDto toDto() {
    return SignUpDto.create(authType, authId, name, nickname, gender);
  }
}
