package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.repository.domain.entity.AuthType;
import com.project.chamjimayo.repository.domain.entity.Role;
import com.project.chamjimayo.repository.domain.entity.User;
import lombok.Getter;

@Getter
public class SignUpDto {

  private final AuthType authType;
  private final String authId;

  private final String name;
  private final String nickname;

  private final String userProfile;

  private final String gender;

  private SignUpDto(AuthType authType, String authId, String name, String nickname,
      String userProfile, String gender) {
    this.authType = authType;
    this.authId = authId;
    this.name = name;
    this.nickname = nickname;
    this.userProfile = userProfile;
    this.gender = gender;
  }

  public static SignUpDto create(AuthType authType, String authId, String name, String nickname,
      String userProfile, String gender) {
    return new SignUpDto(authType, authId, name, nickname, userProfile, gender);
  }

  public User toEntity() {
    return User.createNewUser(name, nickname,gender, userProfile, authId, authType);
  }
}
