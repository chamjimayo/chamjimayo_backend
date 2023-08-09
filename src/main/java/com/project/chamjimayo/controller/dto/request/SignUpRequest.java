package com.project.chamjimayo.controller.dto.request;

import com.project.chamjimayo.repository.domain.entity.AuthType;
import com.project.chamjimayo.service.dto.SignUpDto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

  @NotNull
  @Schema(description = "인증 플랫폼")
  private AuthType authType;

  @NotBlank
  @Schema(description = "사용자 식별 아이디")
  private String authId;

  @NotBlank
  @Schema(description = "사용자 이름")
  private String name;

  @NotBlank
  @Schema(description = "사용자 닉네임")
  private String nickname;

  @NotBlank
  @Pattern(regexp = "^(female|male)")
  @Schema(description = "성별")
  private String gender;

  public SignUpDto toDto() {
    return SignUpDto.create(authType, authId, name, nickname, gender);
  }
}
