package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.UserDetailsResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserDetailsDto {
  private final String name;
  private final String nickname;
  private final Integer point;

  public UserDetailsDto(String name, String nickname, Integer point) {
    this.name = name;
    this.nickname = nickname;
    this.point = point;
  }

  public UserDetailsResponse toResponse() {
    return UserDetailsResponse.create(name, nickname, point);
  }
}
