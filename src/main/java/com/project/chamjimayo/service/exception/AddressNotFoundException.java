package com.project.chamjimayo.service.exception;

public class AddressNotFoundException extends RuntimeException {

  public AddressNotFoundException(String msg) {
    super(msg);
  }

  public ErrorStatus toErrorCode() {
    return ErrorStatus.ADDRESS_NOT_FOUND;
  }
}
