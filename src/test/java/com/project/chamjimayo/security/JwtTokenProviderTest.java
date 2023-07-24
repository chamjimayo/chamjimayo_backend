package com.project.chamjimayo.security;


import static org.junit.jupiter.api.Assertions.*;

import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.security.config.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

  public final JwtTokenProvider jwtTokenProvider;
  public final JwtTokenProvider expiredJwtTokenProvider;
  public final static String PAYLOAD = "1";

  public JwtTokenProviderTest() {
    jwtTokenProvider = new JwtTokenProvider(createJwtProperties(80000L));

    expiredJwtTokenProvider = new JwtTokenProvider(createJwtProperties(0L));
  }

  private JwtProperties createJwtProperties(long tokenValidityInMilliseconds) {
    JwtProperties jwtProperties = new JwtProperties();
    jwtProperties.setSecretKey("T".repeat(32));
    jwtProperties.setAccessTokenValidityInMilliseconds(tokenValidityInMilliseconds);
    jwtProperties.setRefreshTokenValidityInMilliseconds(tokenValidityInMilliseconds);

    return jwtProperties;
  }

  @DisplayName("엑세스 토큰을 생성한다.")
  @Test
  void createAccessToken() {
    String accessToken = jwtTokenProvider.createAccessToken(PAYLOAD);

    assertFalse(accessToken.isEmpty());
  }

  @DisplayName("리프레시 토큰을 생성한다.")
  @Test
  void createRefreshToken() {
    String refreshToken = jwtTokenProvider.createRefreshToken(PAYLOAD);

    assertFalse(refreshToken.isEmpty());
  }

  @DisplayName("액세스 토큰으로부터 payload 추출")
  @Test
  void getPayloadByAccessToken() {
    String accessToken = jwtTokenProvider.createAccessToken(PAYLOAD);

    String payload = jwtTokenProvider.getPayload(accessToken);

    assertEquals(PAYLOAD, payload);
  }

  @DisplayName("리프레시 토큰으로부터 payload 추출")
  @Test
  void getPayloadByRefreshToken() {
    String refreshToken = jwtTokenProvider.createRefreshToken(PAYLOAD);

    String payload = jwtTokenProvider.getPayload(refreshToken);

    assertEquals(PAYLOAD, payload);
  }

  @DisplayName("유효하지 않은 토큰으로 payload 추출")
  @Test
  void getPayloadByNotValidToken() {
    String trash = "trash";

    assertThrows(InvalidTokenException.class, () -> jwtTokenProvider.getPayload(trash));
  }

  @DisplayName("유효기간 지난 토큰으로 payload 추출")
  @Test
  void getPayloadByExpiredToken() {
    String accessToken = expiredJwtTokenProvider.createAccessToken(PAYLOAD);

    assertThrows(InvalidTokenException.class, () -> jwtTokenProvider.getPayload(accessToken));
  }

  @DisplayName("유효한 토큰으로 유효한지 검증")
  @Test
  void isValidByValidToken() {
    String accessToken = jwtTokenProvider.createAccessToken(PAYLOAD);

    assertTrue(jwtTokenProvider.isValid(accessToken));
  }

  @DisplayName("유효하지 않은 토큰으로 유효한지 검증")
  @Test
  void isValidByNotValidToken() {
    String trash = "trash";

    assertFalse(jwtTokenProvider.isValid(trash));
  }

  @DisplayName("만료된 토큰으로 유효한지 검증")
  @Test
  void isValidByExpiredToken() {
    String accessToken = expiredJwtTokenProvider.createAccessToken(PAYLOAD);

    assertFalse(jwtTokenProvider.isValid(accessToken));
  }
}