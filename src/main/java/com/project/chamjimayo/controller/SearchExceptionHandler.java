package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.ApiNotFoundException;
import com.project.chamjimayo.exception.JsonFileNotFoundException;
import com.project.chamjimayo.exception.SearchHistoryNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {SearchController.class})
public class SearchExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(SearchHistoryNotFoundException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleInvalidSearchHistoryNotFoundException(
		SearchHistoryNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.SEARCH_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(ApiNotFoundException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleApiNotFoundException(ApiNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.API_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(JsonFileNotFoundException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleInvalidJsonFileNotFoundException(
		JsonFileNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.JSON_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ApiStandardResponse.fail(errorResponse));
	}
}
