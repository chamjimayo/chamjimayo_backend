package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class InvalidTokenException extends RuntimeException {

  public InvalidTokenException(final String message) {
    super(message);
  }

  public ErrorCode toErrorCode() {
    return ErrorCode.INVALID_TOKEN_EXCEPTION;
  }
}
