package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class ReviewNotFoundException extends RuntimeException {

  public ReviewNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.REVIEW_NOT_FOUND;
  }
}
