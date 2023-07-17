package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class SearchHistoryNotFoundException extends RuntimeException {

	public SearchHistoryNotFoundException(String msg) {
		super(msg);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.SEARCH_NOT_FOUND;
	}
}
