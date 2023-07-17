package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.ErrorStatus;
import com.project.chamjimayo.exception.ApiNotFoundException;
import com.project.chamjimayo.exception.JsonFileNotFoundException;
import com.project.chamjimayo.exception.SearchHistoryNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice(assignableTypes = {SearchController.class})
public class SearchExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiStandardResponse<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(SearchHistoryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiStandardResponse<ErrorResponse> handleSearchHistoryNotFoundException(SearchHistoryNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(ApiNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiStandardResponse<ErrorResponse> handleApiNotFoundException(ApiNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}


	@ExceptionHandler(JsonFileNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiStandardResponse<ErrorResponse> handleJsonFileNotFoundException(JsonFileNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiStandardResponse<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INVALID_PARAMETER, "올바르지 않은 파라미터 값입니다.");
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiStandardResponse<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.NEED_MORE_PARAMETER, "파라미터가 부족합니다.");
		return ApiStandardResponse.fail(errorResponse);
	}
}
