package com.project.chamjimayo.service.exception;

public class PointLackException extends RuntimeException {

  public PointLackException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.POINT_NOT_ENOUGH;
  }

}
