package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class UserDuplicateException extends RuntimeException {

	public UserDuplicateException(final String message) {
		super(message);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.USER_DUPLICATE_EXCEPTION;
	}
}
