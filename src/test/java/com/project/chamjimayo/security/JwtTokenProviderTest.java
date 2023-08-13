package com.project.chamjimayo.security;


import static org.junit.jupiter.api.Assertions.*;

import com.project.chamjimayo.security.exception.InvalidTokenException;
import com.project.chamjimayo.security.config.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class JwtTokenProviderTest {

  public final JwtTokenProvider sut;
  public final JwtTokenProvider expiredJwtTokenProvider;
  public final static String PAYLOAD = "1";

  public JwtTokenProviderTest() {
    sut = new JwtTokenProvider(createJwtProperties(80000L));

    expiredJwtTokenProvider = new JwtTokenProvider(createJwtProperties(0L));
  }

  private JwtProperties createJwtProperties(long tokenValidityInMilliseconds) {
    JwtProperties jwtProperties = new JwtProperties();
    jwtProperties.setSecretKey("T".repeat(32));
    jwtProperties.setAccessTokenValidityMs(tokenValidityInMilliseconds);
    jwtProperties.setRefreshTokenValidityMs(tokenValidityInMilliseconds);

    return jwtProperties;
  }

  @DisplayName("엑세스 토큰을 생성한다.")
  @Test
  void createAccessToken() {
    String accessToken = sut.createAccessToken(PAYLOAD);

    assertFalse(accessToken.isEmpty());
  }

  @DisplayName("리프레시 토큰을 생성한다.")
  @Test
  void createRefreshToken() {
    String refreshToken = sut.createRefreshToken(PAYLOAD);

    assertFalse(refreshToken.isEmpty());
  }

  @DisplayName("액세스 토큰으로부터 payload 추출")
  @Test
  void getPayloadByAccessToken() {
    String accessToken = sut.createAccessToken(PAYLOAD);

    String payload = sut.getPayload(accessToken);

    assertEquals(PAYLOAD, payload);
  }

  @DisplayName("리프레시 토큰으로부터 payload 추출")
  @Test
  void getPayloadByRefreshToken() {
    String refreshToken = sut.createRefreshToken(PAYLOAD);

    String payload = sut.getPayload(refreshToken);

    assertEquals(PAYLOAD, payload);
  }

  @DisplayName("유효하지 않은 토큰으로 payload 추출")
  @Test
  void getPayloadByNotValidToken() {
    String trash = "trash";

    assertThrows(InvalidTokenException.class, () -> sut.getPayload(trash));
  }

  @DisplayName("유효기간 지난 토큰으로 payload 추출")
  @Test
  void getPayloadByExpiredToken() {
    String accessToken = expiredJwtTokenProvider.createAccessToken(PAYLOAD);

    assertThrows(InvalidTokenException.class, () -> sut.getPayload(accessToken));
  }

  @DisplayName("유효한 토큰으로 유효한지 검증")
  @Test
  void isValidByValidToken() {
    String accessToken = sut.createAccessToken(PAYLOAD);

    assertTrue(sut.isValid(accessToken));
  }

  @ParameterizedTest
  @ValueSource(strings = {"trash", "", " "})
  @NullSource
  @DisplayName("유효하지 않은 토큰으로 유효한지 검증")
  void isValidByNotValidToken(String token) {
    assertFalse(sut.isValid(token));
  }

  @DisplayName("만료된 토큰으로 유효한지 검증")
  @Test
  void isValidByExpiredToken() {
    String accessToken = expiredJwtTokenProvider.createAccessToken(PAYLOAD);

    assertFalse(sut.isValid(accessToken));
  }

  @DisplayName("유효기간이 지난 토큰으로 만료되었는지 검증")
  @Test
  void isExpiredByExpiredToken() {
    String accessToken =  expiredJwtTokenProvider.createAccessToken(PAYLOAD);

    assertTrue(sut.isExpired(accessToken));
  }

  @DisplayName("유효기간이 지나지 않은 토큰으로 만료되었는지 검증")
  @Test
  void isNotExpiredByNotExpiredToken() {
    String accessToken = sut.createAccessToken(PAYLOAD);

    assertFalse(sut.isExpired(accessToken));
  }

  @DisplayName("오염된 토큰으로 예외를 던지는지 검증")
  @ValueSource(strings = {"trash", "", " "})
  @NullSource
  @ParameterizedTest
  void isExpiredThrowExceptionByNotValidToken(String token) {
    assertThrows(InvalidTokenException.class, () -> sut.isExpired(token));
  }
}