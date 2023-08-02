package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class GoogleClientRequestInitializeException extends RuntimeException {

  public GoogleClientRequestInitializeException(String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.INVALID_JSON;
  }
}
