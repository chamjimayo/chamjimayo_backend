package com.project.chamjimayo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AuthTokenDto {

	private final String accessToken;

	private final String refreshToken;

	private final long accessTokenValidityMs;

	private final long refreshTokenValidityMs;

	private AuthTokenDto(String accessToken, String refreshToken, long accessTokenValidityMs,
		long refreshTokenValidityMs) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.accessTokenValidityMs = accessTokenValidityMs;
		this.refreshTokenValidityMs = refreshTokenValidityMs;
	}

	public static AuthTokenDto create(String accessToken, String refreshToken,
		long accessTokenValidityMs, long refreshTokenValidityMs) {
		return new AuthTokenDto(accessToken, refreshToken, accessTokenValidityMs,
			refreshTokenValidityMs);
	}

	@Schema(name = "AuthTokenDto.Response")
	@Getter
	public static class Response {

		private final String accessToken;
		private final String refreshToken;
		private final long accessTokenValidityMs;
		private final long refreshTokenValidityMs;

		private Response(String accessToken, String refreshToken, long accessTokenValidityMs,
			long refreshTokenValidityMs) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.accessTokenValidityMs = accessTokenValidityMs;
			this.refreshTokenValidityMs = refreshTokenValidityMs;
		}
	}

	public Response toResponse() {
		return new Response(accessToken, refreshToken, accessTokenValidityMs,
			refreshTokenValidityMs);
	}
}
