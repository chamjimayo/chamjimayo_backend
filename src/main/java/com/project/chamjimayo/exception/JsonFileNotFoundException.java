package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class JsonFileNotFoundException extends RuntimeException {

	public JsonFileNotFoundException(String msg) {
		super(msg);
	}

	public ErrorCode toErrorCode() {
		return ErrorCode.JSON_NOT_FOUND;
	}
}