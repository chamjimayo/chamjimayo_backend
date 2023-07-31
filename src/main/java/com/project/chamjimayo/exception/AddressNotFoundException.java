package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class AddressNotFoundException extends RuntimeException {

	public AddressNotFoundException(String msg) {
		super(msg);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.ADDRESS_NOT_FOUND;
	}
}
