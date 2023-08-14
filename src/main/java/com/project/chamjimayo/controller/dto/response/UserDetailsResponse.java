package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "UserDetailsDto.Response")
@Getter
public class UserDetailsResponse {
  private final String name;
  private final String nickname;
  private final Integer point;

  private UserDetailsResponse(String name, String nickname, Integer point) {
    this.name = name;
    this.nickname = nickname;
    this.point = point;
  }

  public static UserDetailsResponse create(String name, String nickname, Integer point) {
    return new UserDetailsResponse(name, nickname, point);
  }
}