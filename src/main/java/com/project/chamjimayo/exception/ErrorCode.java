package com.project.chamjimayo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	//400 BAD_REQUEST 잘못된 요청
	INVALID_INPUT_VALUE(400, "COMMON-001", "유효성 검증에 실패한 경우"),
	INVALID_PARAMETER(400, "COMMON-002", "파라미터 값이 유효하지 않은 경우"),
	DUPLICATE_NICK_NAME(400, "USER-001", "닉네임이 중복된 경우"),
	NEED_MORE_PARAMETER(400, "COMMON-003", "파라미터의 수가 부족한 경우"),

	// 401 Unauthorized 인증되지 않음
	UNAUTHORIZED(401, "USER-002", "인증에 실패한 경우"),

	// 403 Forbidden 권한 없음
	ACCESS_DENIED(403, "USER-003", "권한이 없는 경우"),

	//404 NOT_FOUND 잘못된 리소스 접근
	USER_NOT_FOUND(404, "USER-004", "유저를 찾을 수 없는 경우"),
	SEARCH_NOT_FOUND(404, "SEARCH-001", "검색 기록을 찾을 수 없는 경우"),
	TOKEN_NOT_EXISTS(404, "USER-005", "해당 key의 인증 토큰이 존재하지 않는 경우"),
	API_NOT_FOUND(404, "API-001", "T-map API에서 응답을 받지 못한 경우"),
	JSON_NOT_FOUND(404, "JSON-001", "JSON 파일이 온전하지 않은 경우"),

	// 405 METHOD_NOT_ALLOWED 지원되지 않는 HTTP 요청 메서드
	INVALID_HTTP_METHOD(405, "COMMON-004", "지원되지 않는 HTTP 요청 메서드인 경우"),

	//409 CONFLICT 중복된 리소스

	//500 INTERNAL SERVER ERROR 서버 오류
	DATABASE_ERROR(500, "COMMON-005", "데이터 베이스 오류 발생"),
	INTER_SERVER_ERROR(500, "COMMON-006", "서버 오류 발생");

	private int status;
	private String errorCode;
	private String message;

}
