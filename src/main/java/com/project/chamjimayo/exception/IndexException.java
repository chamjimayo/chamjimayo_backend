package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class IndexException extends RuntimeException{
  public IndexException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.INDEX_EXCEPTION;
  }
}
