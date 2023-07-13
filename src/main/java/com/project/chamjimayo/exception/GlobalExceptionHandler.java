package com.project.chamjimayo.exception;

import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 사용자 지정 오류 (ErrorCode에서 관리)
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
		log.error("CustomException", ex);
		ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	// 유효성 검사 오류 (잘못된 파라미터 값)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		log.error("MethodArgumentTypeMismatchException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_PARAMETER);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
	}

	// 파라미터가 부족한 경우
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
		MissingServletRequestParameterException ex) {
		log.error("MissingServletRequestParameterException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.NEED_MORE_PARAMETER);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
	}

	// 데이터 액세스 오류
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
		log.error("DataAccessException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.DATABASE_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
	}

	// 권한 거부 오류
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
		log.error("AccessDeniedException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.ACCESS_DENIED);
		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); // 405
	}

	// 지원되지 않는 HTTP 요청 메서드 오류
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException ex) {
		log.error("HttpRequestMethodNotSupportedException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_HTTP_METHOD);
		return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	// 그 외 오류 (해결할 수 없는 서버 오류)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("handleException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

