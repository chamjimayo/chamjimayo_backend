package com.project.chamjimayo.service.exception;

public class IndexException extends RuntimeException{
  public IndexException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.INDEX_EXCEPTION;
  }
}
