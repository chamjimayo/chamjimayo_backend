package com.project.chamjimayo.service.exception;

public class UsingRestroomException extends RuntimeException{
  public UsingRestroomException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.USING_RESTROOM_EXCEPTION;
  }
}
