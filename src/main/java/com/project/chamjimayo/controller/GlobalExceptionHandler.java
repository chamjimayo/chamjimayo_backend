package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.ApiNotFoundException;
import com.project.chamjimayo.exception.JsonFileNotFoundException;
import com.project.chamjimayo.exception.SearchHistoryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleApiNotFoundException(ApiNotFoundException e) {
		log.error( e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.API_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(JsonFileNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleInvalidJsonFileNotFoundException(JsonFileNotFoundException e) {
		log.error( e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.JSON_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(SearchHistoryNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleInvalidSearchHistoryNotFoundException(SearchHistoryNotFoundException e) {
		log.error( e.getMessage());
		final ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.SEARCH_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("지원하지 않는 HTTP Method입니다.");
		final ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.METHOD_NOT_ALLOWED_EXCEPTION, "지원하지 않는 HTTP Method입니다.");
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> MethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		log.error("올바르지 않은 파라미터 값입니다.");
		final ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.INVALID_PARAMETER, "올바르지 않은 파라미터 값입니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> MissingServletRequestParameterException(
		MissingServletRequestParameterException e) {
		log.error("파라미터가 부족합니다.");
		final ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.NEED_MORE_PARAMETER, "파라미터가 부족합니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorResponse> handleDatabaseException(DataAccessException e) {
		log.error("데이터베이스에 오류가 발생했습니다.");
		final ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.DATABASE_ERROR, "데이터베이스에 오류가 발생했습니다.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("예상치 못한 서버 오류가 발생했습니다.");
		final ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.INTERNAL_SERVER_ERROR, "예상치 못한 서버 오류가 발생했습니다.");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
