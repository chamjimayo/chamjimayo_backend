package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.ErrorStatus;
import com.project.chamjimayo.exception.ApiNotFoundException;
import com.project.chamjimayo.exception.JsonFileNotFoundException;
import com.project.chamjimayo.exception.SearchHistoryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiStandardResponse<ErrorResponse> handleApiNotFoundException(ApiNotFoundException e) {
    log.error("", e);
    ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.API_NOT_FOUND, e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(JsonFileNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiStandardResponse<ErrorResponse> handleInvalidJsonFileNotFoundException(
      JsonFileNotFoundException e) {
    log.error("", e);
    ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.JSON_NOT_FOUND, e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(SearchHistoryNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiStandardResponse<ErrorResponse> handleInvalidSearchHistoryNotFoundException(
      SearchHistoryNotFoundException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.SEARCH_NOT_FOUND,
        e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ApiStandardResponse<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(
        ErrorStatus.METHOD_NOT_ALLOWED_EXCEPTION, "지원하지 않는 HTTP Method입니다.");
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INVALID_PARAMETER,
        "올바르지 않은 파라미터 값입니다.");
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.NEED_MORE_PARAMETER,
        "파라미터가 부족합니다.");
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(DataAccessException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiStandardResponse<ErrorResponse> handleDatabaseException(DataAccessException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.DATABASE_ERROR,
        "데이터베이스에 오류가 발생했습니다.");
    return ApiStandardResponse.fail(errorResponse);
  }
}
