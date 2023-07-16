package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class ApiNotFoundException extends RuntimeException {

	public ApiNotFoundException(String msg) {
		super(msg);
	}

	public ErrorCode toErrorCode() {
		return ErrorCode.API_NOT_FOUND;
	}
}
