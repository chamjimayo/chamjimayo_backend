package com.project.chamjimayo.security.exception;

import com.project.chamjimayo.service.exception.ErrorStatus;

public class ApiKeyNotValidException extends RuntimeException {

  public ApiKeyNotValidException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.API_KEY_NOT_VALID_EXCEPTION;
  }
}
