package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

  @ExceptionHandler(UserNickNameDuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleUserNickNameDuplicateException(
      UserNickNameDuplicateException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiStandardResponse.fail(errorResponse, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(UserDuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleIllegalUserDuplicateException(
      UserDuplicateException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiStandardResponse.fail(errorResponse, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleUserNotFoundException(UserNotFoundException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiStandardResponse.fail(errorResponse, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(AuthException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleAuthException(AuthException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiStandardResponse.fail(errorResponse, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(InvalidTokenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiStandardResponse<ErrorResponse>> handlerInvalidTokenException(InvalidTokenException e) {
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiStandardResponse.fail(errorResponse, HttpStatus.BAD_REQUEST.value()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiStandardResponse<ErrorResponse>> handlerMethodArgumentNotValidException() {
    final ErrorResponse errorResponse = ErrorResponse.create(
        ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, "요청이 올바르지 않습니다.");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiStandardResponse.fail(errorResponse, HttpStatus.BAD_REQUEST.value()));
  }
}
