package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.service.dto.IssueTokenDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueTokenRequest {
  private String authId;
  private String refreshToken;

  public IssueTokenDto toDto() {
    return IssueTokenDto.create(authId, refreshToken);
  }
}
