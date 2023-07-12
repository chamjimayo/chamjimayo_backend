package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class UserNickNameDuplicateException extends RuntimeException {

  public UserNickNameDuplicateException(final String message) {
    super(message);
  }

  public ErrorCode toErrorCode() {
    return ErrorCode.USER_NICK_NAME_DUPLICATE_EXCEPTION;
  }
}
