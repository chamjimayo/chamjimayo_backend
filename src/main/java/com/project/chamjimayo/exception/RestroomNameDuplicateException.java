package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorCode;

public class RestroomNameDuplicateException extends RuntimeException {
    public RestroomNameDuplicateException(final String message) {
        super(message);
    }

    public ErrorCode toErrorCode() {
        return ErrorCode.RESTROOM_NAME_DUPLICATE_EXCEPTION;
    }
}
