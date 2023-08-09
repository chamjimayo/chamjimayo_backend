package com.project.chamjimayo.service.exception;

public class RestroomNameDuplicateException extends RuntimeException {

  public RestroomNameDuplicateException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.RESTROOM_NAME_DUPLICATE_EXCEPTION;
  }
}
