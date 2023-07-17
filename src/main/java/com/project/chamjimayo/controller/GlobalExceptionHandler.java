package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import javax.validation.ValidationException;
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

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> HttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException e) {
		log.error("지원하지 않는 HTTP Method입니다.");
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.METHOD_NOT_ALLOWED_EXCEPTION, "지원하지 않는 HTTP Method입니다.");
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleValidationException(ValidationException e) {
		log.error("유효성 검사에 실패했습니다.");
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.INVALID_PARAMETER, "올바른 파라미터를 입력하세요.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> MethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException e) {
		log.error("올바르지 않은 파라미터 값입니다.");
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.INVALID_PARAMETER, "올바르지 않은 파라미터 값입니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> MissingServletRequestParameterException(
		MissingServletRequestParameterException e) {
		log.error("파라미터가 부족합니다.");
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.NEED_MORE_PARAMETER, "파라미터가 부족합니다.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleDatabaseException(DataAccessException e) {
		log.error("데이터베이스에 오류가 발생했습니다.");
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.DATABASE_ERROR, "데이터베이스에 오류가 발생했습니다.");
		return ResponseEntity.status(501)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	// test 단계에서 예상치 못한 오류 파악을 위한 주석 처리
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleException(Exception e) {
//		log.error("서버 내부 오류가 발생했습니다.");
//		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//			.body(ApiStandardResponse.fail(errorResponse));
//	}
}
