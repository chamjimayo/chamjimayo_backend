package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException(final String message) {
		super(message);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.INVALID_TOKEN_EXCEPTION;
	}
}
