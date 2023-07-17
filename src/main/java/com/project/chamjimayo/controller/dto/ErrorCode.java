package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {
  //400 BAD_REQUEST 잘못된 요청
  INVALID_PARAMETER("01"),
  NEED_MORE_PARAMETER("02"),
  USER_DUPLICATE_EXCEPTION("03"),
  USER_NICK_NAME_DUPLICATE_EXCEPTION("04"),
  AUTH_EXCEPTION("05"),
  INVALID_TOKEN_EXCEPTION("06"),

  // 401 Unauthorized 인증되지 않음
  AUTHENTICATION_EXCEPTION("07"),

  // 403 Forbidden 권한 없음

  // 404 NOT_FOUND 잘못된 리소스 접근
  USER_NOT_FOUND_EXCEPTION("08"),
  SEARCH_NOT_FOUND("09"),
  API_NOT_FOUND("10"),
  JSON_NOT_FOUND("11"),
  REVIEW_NOT_FOUND("12"),
  RESTROOM_NOT_FOUND("13"),

  // 405 METHOD_NOT_ALLOWED 지원되지 않는 HTTP 요청 메서드
  METHOD_NOT_ALLOWED_EXCEPTION("14"),
  METHOD_ARGUMENT_NOT_VALID_EXCEPTION("15"),

  // 500 INTERNAL SERVER ERROR 서버 오류
  INTERNAL_SERVER_ERROR("16"),

  // 501 DATABASE ERROR 데이터베이스 오류 (서버 오류)
  DATABASE_ERROR("17");

  private final String code;

  ErrorCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }
}
