package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.domain.entity.AuthType;
import com.project.chamjimayo.domain.entity.Role;
import com.project.chamjimayo.domain.entity.User;
import lombok.Getter;

@Getter
public class SignUpDto {

  private final AuthType authType;
  private final String authId;
  private final String name;
  private final String nickname;
  private final String gender;

  private SignUpDto(AuthType authType, String authId, String name, String nickname, String gender) {
    this.authType = authType;
    this.authId = authId;
    this.name = name;
    this.nickname = nickname;
    this.gender = gender;
  }

  public static SignUpDto create(AuthType authType, String authId, String name, String nickname,
      String gender) {
    return new SignUpDto(authType, authId, name, nickname, gender);
  }

  public User createNewUser() {
    return User.builder()
        .name(name)
        .nickname(nickname)
        .gender(gender)
        .authId(authId)
        .authType(authType)
        .point(0)
        .role(Role.ROLE_USER)
        .build();
  }
}
