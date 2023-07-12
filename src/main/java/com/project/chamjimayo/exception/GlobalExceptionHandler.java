package com.project.chamjimayo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 사옹자 지정 오류 (ErrorCode에서 관리)
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> CustomException(CustomException ex) {
		log.error("CustomException", ex);
		ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	// 유효성 검사 오류 (잘못된 파라미터 값)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> MethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException ex) {
		log.error("MethodArgumentTypeMismatchException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_PARAMETER);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
	}

	// 파라미터가 부족한 경우
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> MissingServletRequestParameterException(
		MissingServletRequestParameterException ex) {
		log.error("MissingServletRequestParameterException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.NEED_MORE_PARAMETER);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400
	}

	// 그 외 오류 (해결할 수 없는 서버 오류)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("handleException", ex);
		ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
