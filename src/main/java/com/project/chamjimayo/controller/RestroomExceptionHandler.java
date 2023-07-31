package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.AddressNotFoundException;
import com.project.chamjimayo.exception.FileNotFoundException;
import com.project.chamjimayo.exception.IoException;
import com.project.chamjimayo.exception.RestroomNameDuplicateException;
import com.project.chamjimayo.exception.RestroomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {RestroomController.class})
public class RestroomExceptionHandler {

	@ExceptionHandler(AddressNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiStandardResponse<ErrorResponse> handleAddressNotFoundException(
		AddressNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(RestroomNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiStandardResponse<ErrorResponse> handleRestroomNotFoundException(
		RestroomNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(RestroomNameDuplicateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiStandardResponse<ErrorResponse> handleRestroomNameDuplicateException(
		RestroomNameDuplicateException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiStandardResponse<ErrorResponse> handleFileNotFoundException(
		FileNotFoundException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}

	@ExceptionHandler(IoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiStandardResponse<ErrorResponse> handleIoException(
		IoException e) {
		log.error("", e);

		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
		return ApiStandardResponse.fail(errorResponse);
	}
}
