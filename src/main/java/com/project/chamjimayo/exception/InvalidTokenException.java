package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;
import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

  public InvalidTokenException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.INVALID_TOKEN_EXCEPTION;
  }
}
