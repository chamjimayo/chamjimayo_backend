package com.project.chamjimayo.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshAccessTokenRequest {

  @NotBlank
  @Schema(description = "리프레시 토큰")
  private String refreshToken;

}
