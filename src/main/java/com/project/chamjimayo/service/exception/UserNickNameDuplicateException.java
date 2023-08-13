package com.project.chamjimayo.service.exception;

public class UserNickNameDuplicateException extends RuntimeException {

  public UserNickNameDuplicateException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.USER_NICK_NAME_DUPLICATE_EXCEPTION;
  }
}
