package com.project.chamjimayo.exception;

public class UserDuplicateException extends RuntimeException {

  public UserDuplicateException(final String message) {
    super(message);
  }
}
