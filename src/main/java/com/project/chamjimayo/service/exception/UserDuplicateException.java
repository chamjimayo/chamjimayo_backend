package com.project.chamjimayo.service.exception;

public class UserDuplicateException extends RuntimeException {

  public UserDuplicateException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.USER_DUPLICATE_EXCEPTION;
  }
}
