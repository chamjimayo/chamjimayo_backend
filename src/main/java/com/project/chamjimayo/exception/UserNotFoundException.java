package com.project.chamjimayo.exception;

public class UserNotFoundException extends RuntimeException{

  public UserNotFoundException(String msg) {
    super(msg);
  }
}
