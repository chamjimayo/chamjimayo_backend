package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class PointLackException extends RuntimeException {

  public PointLackException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.POINT_NOT_ENOUGH;
  }

}
