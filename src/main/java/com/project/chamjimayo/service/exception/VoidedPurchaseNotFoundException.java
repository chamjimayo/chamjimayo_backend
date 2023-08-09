package com.project.chamjimayo.service.exception;

public class VoidedPurchaseNotFoundException extends RuntimeException{
  public VoidedPurchaseNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.VOIDED_PURCHASE_NOT_FOUND;
  }
}
