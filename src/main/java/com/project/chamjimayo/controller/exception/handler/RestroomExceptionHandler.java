package com.project.chamjimayo.controller.exception.handler;

import com.project.chamjimayo.service.exception.PageOutOfRangeException;
import com.project.chamjimayo.controller.RestroomController;
import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.service.exception.AddressNotFoundException;
import com.project.chamjimayo.service.exception.ErrorStatus;
import com.project.chamjimayo.service.exception.FileNotFoundException;
import com.project.chamjimayo.service.exception.IoException;
import com.project.chamjimayo.service.exception.PointLackException;
import com.project.chamjimayo.service.exception.RestroomNameDuplicateException;
import com.project.chamjimayo.service.exception.RestroomNotFoundException;
import com.project.chamjimayo.service.exception.UsingRestroomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {RestroomController.class})
public class RestroomExceptionHandler {

  @ExceptionHandler(AddressNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleAddressNotFoundException(
      AddressNotFoundException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(RestroomNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleRestroomNotFoundException(
      RestroomNotFoundException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(RestroomNameDuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleRestroomNameDuplicateException(
      RestroomNameDuplicateException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(FileNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleFileNotFoundException(
      FileNotFoundException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }

  @ExceptionHandler(IoException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handleIoException(
      IoException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }
  @ExceptionHandler(IndexOutOfBoundsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> IndexOutOfBoundsException(
      IndexOutOfBoundsException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INDEX_EXCEPTION,"잘못된 인덱스 요청입니다");
    return ApiStandardResponse.fail(errorResponse);
  }
  @ExceptionHandler(PageOutOfRangeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> PageOutOfRangeException(
      PageOutOfRangeException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(),e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }
  @ExceptionHandler(UsingRestroomException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> UsingRestroomException(
      UsingRestroomException e) {
    log.error("", e);
    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(),e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }
  @ExceptionHandler(PointLackException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiStandardResponse<ErrorResponse> handlePointLackException(PointLackException e) {
    log.error("", e);

    final ErrorResponse errorResponse = ErrorResponse.create(e.toErrorCode(), e.getMessage());
    return ApiStandardResponse.fail(errorResponse);
  }
}
