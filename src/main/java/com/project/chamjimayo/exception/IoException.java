package com.project.chamjimayo.exception;

import com.project.chamjimayo.controller.dto.ErrorStatus;

public class IoException extends RuntimeException{
    public IoException(String msg) {
        super(msg);
    }

    public ErrorStatus toErrorCode() {
        return ErrorStatus.IOEXCEPTION;
    }
}
