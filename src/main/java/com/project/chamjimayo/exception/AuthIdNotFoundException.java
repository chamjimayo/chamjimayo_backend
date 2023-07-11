package com.project.chamjimayo.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthIdNotFoundException extends AuthenticationException {

  public AuthIdNotFoundException(String msg) {
    super(msg);
  }
}
