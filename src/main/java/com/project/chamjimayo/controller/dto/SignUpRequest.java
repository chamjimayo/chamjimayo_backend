package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.AuthType;
import com.project.chamjimayo.service.dto.SignUpDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

  @NotNull
  private AuthType authType;

  @NotBlank
  private String authId;

  @NotBlank
  private String name;

  @NotBlank
  private String nickname;

  @NotBlank
  @Pattern(regexp = "(?:^|\\W)(female|male)(?:$|\\W)")
  private String gender;

  public SignUpDto toDto() {
    return SignUpDto.create(authType, authId, name, nickname, gender);
  }
}
