package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class AuthException extends RuntimeException {

	public AuthException(String msg) {
		super(msg);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.AUTH_EXCEPTION;
	}
}
