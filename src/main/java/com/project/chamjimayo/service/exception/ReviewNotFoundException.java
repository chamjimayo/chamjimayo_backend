package com.project.chamjimayo.service.exception;

import com.project.chamjimayo.service.exception.ErrorStatus;

public class ReviewNotFoundException extends RuntimeException {

  public ReviewNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.REVIEW_NOT_FOUND;
  }
}
