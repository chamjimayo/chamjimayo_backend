package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.exception.UserDuplicateException;
import com.project.chamjimayo.exception.UserNickNameDuplicateException;
import com.project.chamjimayo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

  @ExceptionHandler(UserNickNameDuplicateException.class)
  public ResponseEntity<ErrorResponse> handleUserNickNameDuplicateException(
      UserNickNameDuplicateException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(UserDuplicateException.class)
  public ResponseEntity<ErrorResponse> handleIllegalUserDuplicateException(
      UserDuplicateException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<ErrorResponse> handlerInvalidTokenException(InvalidTokenException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException() {
    final ErrorResponse errorResponse = ErrorResponse.create(
        ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, "요청이 올바르지 않습니다.");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
}
