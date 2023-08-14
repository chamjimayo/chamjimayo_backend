package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Getter;

@Schema(name = "AuthTokenDto.Response")
@Getter
public class AuthTokenResponse {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  private final String accessToken;
  private final String refreshToken;
  private final String accessTokenExpiredDate;
  private final String refreshTokenExpiredDate;

  private AuthTokenResponse(String accessToken, String refreshToken, String accessTokenExpiredDate,
      String refreshTokenExpiredDate) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenExpiredDate = accessTokenExpiredDate;
    this.refreshTokenExpiredDate = refreshTokenExpiredDate;
  }

  public static AuthTokenResponse create(String accessToken, String refreshToken,
      long accessTokenValidityMs, long refreshTokenValidityMs) {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    return new AuthTokenResponse(accessToken, refreshToken,
        formattedExpiredDate(now, accessTokenValidityMs),
        formattedExpiredDate(now, refreshTokenValidityMs));
  }

  private static String formattedExpiredDate(ZonedDateTime now, long validityMs) {
    return FORMATTER.format(now.plus(validityMs, ChronoUnit.MILLIS));
  }
}