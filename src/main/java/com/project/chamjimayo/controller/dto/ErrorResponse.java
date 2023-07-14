package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

  @Schema(description = "에러 코드")
  private final ErrorCode code;

  @Schema(description = "에러 메시지")
  private final String msg;

  public ErrorResponse(ErrorCode code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ErrorResponse create(ErrorCode code, String msg) {
    return new ErrorResponse(code, msg);
  }
}
