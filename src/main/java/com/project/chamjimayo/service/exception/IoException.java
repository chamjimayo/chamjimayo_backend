package com.project.chamjimayo.service.exception;

public class IoException extends RuntimeException {

  public IoException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.IOEXCEPTION;
  }
}
