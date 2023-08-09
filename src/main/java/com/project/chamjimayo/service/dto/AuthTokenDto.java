package com.project.chamjimayo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Getter
public class AuthTokenDto {
  private final String accessToken;
  private final String refreshToken;

  private final long accessTokenValidityMs;
  private final long refreshTokenValidityMs;

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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
    private final String accessTokenExpiredDate;
    private final String refreshTokenExpiredDate;

    private Response(String accessToken, String refreshToken, String accessTokenExpiredDate,
        String refreshTokenExpiredDate) {
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
      this.accessTokenExpiredDate = accessTokenExpiredDate;
      this.refreshTokenExpiredDate = refreshTokenExpiredDate;
    }
  }

  public Response toResponse() {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    return new Response(accessToken, refreshToken, formattedExpiredDate(now, accessTokenValidityMs),
        formattedExpiredDate(now, refreshTokenValidityMs));
  }

  private String formattedExpiredDate(ZonedDateTime now, long validityMs) {
    return FORMATTER.format(now.plus(validityMs, ChronoUnit.MILLIS));
  }
}
