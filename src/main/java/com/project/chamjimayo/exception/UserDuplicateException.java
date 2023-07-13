package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class UserDuplicateException extends RuntimeException {

  public UserDuplicateException(final String message) {
    super(message);
  }

  public ErrorCode toErrorCode() {
    return ErrorCode.USER_DUPLICATE_EXCEPTION;
  }
}
