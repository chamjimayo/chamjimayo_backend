package com.project.chamjimayo.controller.exception;

import com.project.chamjimayo.service.exception.ErrorStatus;

public class SearchHistoryNotFoundException extends RuntimeException {

  public SearchHistoryNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.SEARCH_NOT_FOUND;
  }
}
