package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class JsonFileNotFoundException extends RuntimeException {

	public JsonFileNotFoundException(String msg) {
		super(msg);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.JSON_NOT_FOUND;
	}
}