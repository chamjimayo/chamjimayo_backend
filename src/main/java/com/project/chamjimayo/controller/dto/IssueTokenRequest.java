package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.service.dto.IssueTokenDto;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueTokenRequest {

  @NotBlank
  private String authId;

  @NotBlank
  private String refreshToken;

  public IssueTokenDto toDto() {
    return IssueTokenDto.create(authId, refreshToken);
  }
}
