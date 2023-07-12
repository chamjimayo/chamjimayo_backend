package com.project.chamjimayo.exception;

public class TokenExpiredException extends RuntimeException {

  public TokenExpiredException(String msg) {
    super(msg);
  }
}
