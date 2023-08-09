package com.project.chamjimayo.service.exception;

public class PurchaseVerificationException extends RuntimeException {
  public PurchaseVerificationException(String message) {
    super(message);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.PURCHASE_VERIFICATION_EXCEPTION;
  }
}
