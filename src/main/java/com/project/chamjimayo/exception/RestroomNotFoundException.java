package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class RestroomNotFoundException extends RuntimeException {

	public RestroomNotFoundException(String msg) {
		super(msg);
	}

	public ErrorCode toErrorCode() {
		return ErrorCode.RESTROOM_NOT_FOUND;
	}
}
