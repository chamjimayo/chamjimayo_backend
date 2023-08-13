package com.project.chamjimayo.controller.exception.handler;

import com.project.chamjimayo.controller.InAppPurchaseController;
import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.service.exception.GoogleClientRequestException;
import com.project.chamjimayo.service.exception.IoException;
import com.project.chamjimayo.service.exception.PurchaseVerificationException;
import com.project.chamjimayo.service.exception.UserNotFoundException;
import com.project.chamjimayo.service.exception.VoidedPurchaseNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {InAppPurchaseController.class})
public class InAppPurchaseExceptionHandler {
  @ExceptionHandler(IoException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handlePackageException(IoException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(VoidedPurchaseNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> voidedPurchaseNotFoundException(
      VoidedPurchaseNotFoundException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(GoogleClientRequestException.class)
  public ApiStandardResponse<ErrorResponse> handleGoogleClientRequestException(
      GoogleClientRequestException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(PurchaseVerificationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handlePurchaseVerificationException(
      PurchaseVerificationException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }
}
