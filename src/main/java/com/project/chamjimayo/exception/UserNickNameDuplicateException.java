package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class UserNickNameDuplicateException extends RuntimeException {

	public UserNickNameDuplicateException(final String message) {
		super(message);
	}

	public ErrorStatus toErrorCode() {
		return ErrorStatus.USER_NICK_NAME_DUPLICATE_EXCEPTION;
	}
}
