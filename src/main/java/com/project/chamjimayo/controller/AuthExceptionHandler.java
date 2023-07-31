package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.ErrorStatus;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.exception.UserDuplicateException;
import com.project.chamjimayo.exception.UserNickNameDuplicateException;
import com.project.chamjimayo.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {AuthController.class})
public class AuthExceptionHandler {

  @ExceptionHandler(UserNickNameDuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleUserNickNameDuplicateException(
      UserNickNameDuplicateException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(UserDuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleIllegalUserDuplicateException(
      UserDuplicateException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(AuthException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleAuthException(AuthException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(InvalidTokenException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handlerInvalidTokenException(
      InvalidTokenException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handlerMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(
        ErrorStatus.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, "요청이 올바르지 않습니다.");
    return ApiStandardResponse.fail(errorResponse);
  }
}
