package com.project.chamjimayo.controller.exception;

import com.project.chamjimayo.service.exception.ErrorStatus;

public class JsonFileNotFoundException extends RuntimeException {

  public JsonFileNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.INVALID_JSON;
  }
}