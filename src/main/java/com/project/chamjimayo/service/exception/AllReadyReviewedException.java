package com.project.chamjimayo.service.exception;

public class AllReadyReviewedException extends RuntimeException {

  public AllReadyReviewedException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.ALL_READY_REVIEW_EXCEPTION;
  }
}
