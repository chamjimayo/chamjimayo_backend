package com.project.chamjimayo.security.exception;

import com.project.chamjimayo.service.exception.ErrorStatus;

public class InvalidTokenException extends RuntimeException {

  public InvalidTokenException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.INVALID_TOKEN_EXCEPTION;
  }
}
