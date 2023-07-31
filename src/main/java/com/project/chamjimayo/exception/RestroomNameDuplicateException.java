package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class RestroomNameDuplicateException extends RuntimeException {

  public RestroomNameDuplicateException(final String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.RESTROOM_NAME_DUPLICATE_EXCEPTION;
  }
}
