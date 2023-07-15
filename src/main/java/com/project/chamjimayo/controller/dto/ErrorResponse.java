package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {

  @Schema(type = "enum", example = "INVALID_TOKEN_EXCEPTION")
  private final ErrorCode code;

  @Schema(type = "string", example = "매개변수 오류입니다. 입력하신 값을 확인해주세요")
  private final String msg;

  public ErrorResponse(ErrorCode code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ErrorResponse create(ErrorCode code, String msg) {
    return new ErrorResponse(code, msg);
  }
}
