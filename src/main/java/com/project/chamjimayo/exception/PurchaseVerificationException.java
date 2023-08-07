package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class PurchaseVerificationException extends RuntimeException {
  public PurchaseVerificationException(String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.PURCHASE_VERIFICATION_EXCEPTION;
  }
}
