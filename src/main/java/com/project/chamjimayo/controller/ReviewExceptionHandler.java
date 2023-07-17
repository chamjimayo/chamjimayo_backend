package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.exception.RestroomNotFoundException;
import com.project.chamjimayo.exception.ReviewNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {ReviewController.class})
public class ReviewExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleReviewNotFoundException(
		ReviewNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.REVIEW_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(RestroomNotFoundException.class)
	public ResponseEntity<ApiStandardResponse<ErrorResponse>> handleRestroomNotFoundException(
		RestroomNotFoundException e) {
		log.error(e.getMessage());
		ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.RESTROOM_NOT_FOUND, e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ApiStandardResponse.fail(errorResponse));
	}

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ErrorResponse> handleAuthException(AuthException e) {
		final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
