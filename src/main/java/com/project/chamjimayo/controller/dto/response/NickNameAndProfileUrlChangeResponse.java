package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NickNameAndProfileUrlChangeResponse {
  @Schema(description = "변경된 닉네임 값")
  private final String nickname;

  @Schema(description = "변경된 프로필 url 값")
  private final String profileUrl;

  private NickNameAndProfileUrlChangeResponse(String nickname, String profileUrl) {
    this.nickname = nickname;
    this.profileUrl = profileUrl;
  }

  public static NickNameAndProfileUrlChangeResponse create(String nickname, String profileUrl) {
    return new NickNameAndProfileUrlChangeResponse(nickname, profileUrl);
  }
}
