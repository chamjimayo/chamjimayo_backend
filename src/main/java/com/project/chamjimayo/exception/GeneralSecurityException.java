package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class GeneralSecurityException extends RuntimeException {

  public GeneralSecurityException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.SECURITY_EXCEPTION;
  }
}
