package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ErrorResponse {
  @Schema(type = "enum", example = "오류에 대한 코드")
  private final ErrorCode code;

  @Schema(type = "string", example = "오류에 대한 간단한 메세지")
  private final String msg;

  public ErrorResponse(ErrorCode code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ErrorResponse create(ErrorCode code, String msg) {
    return new ErrorResponse(code, msg);
  }
}
