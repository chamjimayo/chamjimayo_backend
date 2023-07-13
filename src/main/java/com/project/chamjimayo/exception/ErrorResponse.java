package com.project.chamjimayo.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

	private final int status;
	private final String message;
	private final String code;

	@Builder
	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.message = errorCode.getMessage();
		this.code = errorCode.getErrorCode();
	}

}
