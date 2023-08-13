package com.project.chamjimayo.service.exception;

public class PageOutOfRangeException extends RuntimeException{
  public PageOutOfRangeException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.PAGE_OUT_OF_RANGE;
  }
}

