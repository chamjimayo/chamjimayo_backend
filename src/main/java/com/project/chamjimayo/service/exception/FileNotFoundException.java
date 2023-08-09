package com.project.chamjimayo.service.exception;

public class FileNotFoundException extends RuntimeException {

  public FileNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.FILE_NOT_FOUND;
  }
}
