package com.project.chamjimayo.service.exception;

public class RestroomNotFoundException extends RuntimeException {

  public RestroomNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.RESTROOM_NOT_FOUND;
  }
}
