package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.ErrorStatus;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.exception.PointLackException;
import com.project.chamjimayo.exception.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {UserController.class})
public class UserExceptionHandler {

  // 유저를 찾을 수 없는 경우
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiStandardResponse<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  // 인증 오류
  @ExceptionHandler(AuthException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiStandardResponse<ErrorResponse> handleAuthException(AuthException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  // 포인트가 부족한 경우 (포인트 차감시)
  @ExceptionHandler(PointLackException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handlePointLackException(PointLackException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  // Validation 오류
  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("", e);

    BindingResult result = e.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();

    // error 메세지 추출
    String errors = fieldErrors.stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(", "));

    final ErrorResponse errorResponse = ErrorResponse.create(
        ErrorStatus.VALIDATION_EXCEPTION, errors);
    return ApiStandardResponse.fail(errorResponse);
  }

  // validation 외 JSON 형식 오류
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INVALID_JSON,
        "올바르지 않은 JSON 형식입니다.");
    return ApiStandardResponse.fail(errorResponse);
  }
}
