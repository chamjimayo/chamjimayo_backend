package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {UserController.class})
public class UserExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiStandardResponse<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(AuthException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ApiStandardResponse<ErrorResponse> handleAuthException(AuthException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}
}
