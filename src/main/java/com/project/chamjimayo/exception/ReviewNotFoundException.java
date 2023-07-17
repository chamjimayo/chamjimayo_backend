package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class ReviewNotFoundException extends RuntimeException {

	public ReviewNotFoundException(String msg) {
		super(msg);
	}

	public com.project.chamjimayo.controller.dto.ErrorCode toErrorCode() {
		return ErrorCode.REVIEW_NOT_FOUND;
	}
}
