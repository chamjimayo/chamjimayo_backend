package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class ApiNotFoundException extends RuntimeException {

	public ApiNotFoundException(String msg) {
		super(msg);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.API_NOT_FOUND;
	}
}
