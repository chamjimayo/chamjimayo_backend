package com.project.chamjimayo.controller.dto;

public enum ErrorStatus {
  // 400 BAD_REQUEST 잘못된 요청
  // 401 Unauthorized 인증되지 않음
  // 403 Forbidden 권한 없음
  // 404 NOT_FOUND 잘못된 리소스 접근
  // 405 METHOD_NOT_ALLOWED 지원되지 않는 HTTP 요청 메서드
  // 500 INTERNAL SERVER ERROR 서버 오류
  INVALID_PARAMETER("01"),
  NEED_MORE_PARAMETER("02"),
  USER_DUPLICATE_EXCEPTION("03"),
  USER_NICK_NAME_DUPLICATE_EXCEPTION("04"),
  AUTH_EXCEPTION("05"),
  INVALID_TOKEN_EXCEPTION("06"),
  AUTHENTICATION_EXCEPTION("07"),
  USER_NOT_FOUND_EXCEPTION("08"),
  SEARCH_NOT_FOUND("09"),
  API_NOT_FOUND("10"),
  JSON_NOT_FOUND("11"),
  METHOD_NOT_ALLOWED_EXCEPTION("12"),
  METHOD_ARGUMENT_NOT_VALID_EXCEPTION("13"),
  INTERNAL_SERVER_ERROR("14"),
  DATABASE_ERROR("15"),
  REVIEW_NOT_FOUND("16"),
  RESTROOM_NOT_FOUND("17"),
  FILE_NOT_FOUND("18"),
  IOEXCEPTION("19"),
  RESTROOM_NAME_DUPLICATE_EXCEPTION("20"),
  ADDRESS_NOT_FOUND("21");

  private final String code;

  ErrorStatus(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }
}
