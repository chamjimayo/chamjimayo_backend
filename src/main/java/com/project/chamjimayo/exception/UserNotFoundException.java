package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class UserNotFoundException extends RuntimeException{

  public UserNotFoundException(String msg) {
    super(msg);
  }

  public ErrorCode toErrorCode() {
    return ErrorCode.USER_NOT_FOUND_EXCEPTION;
  }
}
