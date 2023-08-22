package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.UserDetailsResponse;
import lombok.Getter;

@Getter
public class UserDetailsDto {
  private final String name;
  private final String nickname;
  private final Integer point;
  private final String gender;
  private final String userProfile;
  private final Long usingRestroomId;

  public UserDetailsDto(String name, String nickname, Integer point, String gender,
      String userProfile, Long usingRestroomId) {
    this.name = name;
    this.nickname = nickname;
    this.point = point;
    this.gender = gender;
    this.userProfile = userProfile;
    this.usingRestroomId = usingRestroomId;
  }

  public UserDetailsResponse toResponse() {
    return UserDetailsResponse.create(name, nickname, point, gender, userProfile, usingRestroomId);
  }
}
