package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class AuthException extends RuntimeException {
  public AuthException(String msg) {
    super(msg);
  }

  public ErrorCode toErrorCode() {
    return ErrorCode.AUTH_EXCEPTION;
  }
}
