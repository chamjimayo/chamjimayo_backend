package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class VoidedPurchaseNotFoundException extends RuntimeException{
  public VoidedPurchaseNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.VOIDED_PURCHASE_NOT_FOUND;
  }
}
