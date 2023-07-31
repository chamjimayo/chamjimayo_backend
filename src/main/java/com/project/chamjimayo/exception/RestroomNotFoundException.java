package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class RestroomNotFoundException extends RuntimeException {

  public RestroomNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.RESTROOM_NOT_FOUND;
  }
}
