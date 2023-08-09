package com.project.chamjimayo.controller.exception;

import com.project.chamjimayo.service.exception.ErrorStatus;

public class AuthException extends RuntimeException {

  public AuthException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.AUTH_EXCEPTION;
  }
}
