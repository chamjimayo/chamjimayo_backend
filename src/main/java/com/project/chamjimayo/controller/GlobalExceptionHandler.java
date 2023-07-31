package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ApiStandardResponse<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(
        ErrorStatus.METHOD_NOT_ALLOWED_EXCEPTION, "지원하지 않는 HTTP Method입니다.");
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(DataAccessException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiStandardResponse<ErrorResponse> handleDataAccessException(DataAccessException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.DATABASE_ERROR,
        "데이터베이스에 오류가 발생했습니다.");
    return ApiStandardResponse.fail(errorResponse);
  }

//	// test 단계에서 예상치 못한 오류 파악을 위한 주석 처리
//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	public ApiStandardResponse<ErrorResponse> handleException(Exception e) {
//		log.error("", e);
//
//		final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
//		return ApiStandardResponse.fail(errorResponse);
//	}
}
