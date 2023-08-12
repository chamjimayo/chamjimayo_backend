package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class PageOutOfRangeException extends RuntimeException{
  public PageOutOfRangeException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.PAGE_OUT_OF_RANGE;
  }
}

