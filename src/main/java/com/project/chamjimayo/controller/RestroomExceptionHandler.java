package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.RestroomNameDuplicateException;
import com.project.chamjimayo.exception.UserNickNameDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestroomExceptionHandler {
    @ExceptionHandler(RestroomNameDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleUserNickNameDuplicateException(
        RestroomNameDuplicateException e) {
        final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
