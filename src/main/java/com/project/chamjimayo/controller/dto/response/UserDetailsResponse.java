package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "UserDetailsDto.Response")
@Getter
public class UserDetailsResponse {
  private final String name;
  private final String nickname;
  private final Integer point;
  private final String gender;
  private final String userProfile;
  private final boolean isRestroomUsing;

  public UserDetailsResponse(String name, String nickname, Integer point, String gender,
      String userProfile, boolean isRestroomUsing) {
    this.name = name;
    this.nickname = nickname;
    this.point = point;
    this.gender = gender;
    this.userProfile = userProfile;
    this.isRestroomUsing = isRestroomUsing;
  }

  public static UserDetailsResponse create(String name, String nickname, Integer point,
      String gender, String userProfile, Long usingRestroomId) {
    boolean isRestroomUsing = usingRestroomId != null;

    return new UserDetailsResponse(name, nickname, point, gender, userProfile, isRestroomUsing);
  }
}