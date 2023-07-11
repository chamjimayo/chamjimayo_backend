package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.AuthType;
import com.project.chamjimayo.service.dto.SignUpDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {
  private AuthType authType;
  private String authId;
  private String name;
  private String nickname;
  private String gender;

  public SignUpDto toDto() {
    return SignUpDto.create(authType, authId, name, nickname, gender);
  }
}
