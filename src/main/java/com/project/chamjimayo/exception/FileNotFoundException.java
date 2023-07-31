package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class FileNotFoundException extends RuntimeException {

	public FileNotFoundException(String msg) {
		super(msg);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.FILE_NOT_FOUND;
	}
}
