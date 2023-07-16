package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class SearchHistoryNotFoundException extends RuntimeException {

	public SearchHistoryNotFoundException(String msg) {
		super(msg);
	}

	public com.project.chamjimayo.controller.dto.ErrorCode toErrorCode() {
		return ErrorCode.SEARCH_NOT_FOUND;
	}
}
