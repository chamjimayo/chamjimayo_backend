package com.project.chamjimayo.controller.dto;

public class ErrorResponse {
  private final ErrorCode code;
  private final String msg;

  public ErrorResponse(ErrorCode code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static ErrorResponse create(ErrorCode code, String msg) {
    return new ErrorResponse(code, msg);
  }
}
