package com.project.chamjimayo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.project.chamjimayo.domain.entity.Token;
import com.project.chamjimayo.repository.TokenRepository;
import com.project.chamjimayo.security.JwtTokenProvider;
import com.project.chamjimayo.service.dto.AuthTokenDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthTokenServiceTest {

  @Mock
  private TokenRepository tokenRepository;

  @Mock
  private JwtTokenProvider jwtTokenProvider;

  @InjectMocks
  private AuthTokenService sut;

  private String userId;
  private String accessToken;
  private String refreshToken;

  @BeforeEach
  void setup() {
    userId = "tester";
    accessToken = "accessToken";
    refreshToken = "refreshToken";
  }

  @DisplayName("리프레시 토큰이 존재하는 유저의 인증 토큰을 생성한다.")
  @Test
  void createAuthToken() {
    Token token = Token.create(userId, refreshToken);

    when(jwtTokenProvider.createAccessToken(userId)).thenReturn(accessToken);
    when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.of(token));

    AuthTokenDto authToken = sut.createAuthToken(userId);

    assertEquals(refreshToken, authToken.getRefreshToken());
  }

  @DisplayName("리프레시 토큰이 존재하는 않는 유저의 인증 토큰을 생성한다.")
  @Test
  void createAuthTokenWhenRefreshTokenNotExist() {
    String newRefreshToken = "newRefreshToken";
    Token token = Token.create(userId, newRefreshToken);

    when(jwtTokenProvider.createAccessToken(userId)).thenReturn(accessToken);
    when(jwtTokenProvider.createRefreshToken(userId)).thenReturn(newRefreshToken);
    when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.empty());
    when(tokenRepository.save(any(Token.class))).thenReturn(token);

    AuthTokenDto authToken = sut.createAuthToken(userId);

    assertEquals(newRefreshToken, authToken.getRefreshToken());
  }

  @DisplayName("리프레시 토큰이 만료된 유저의 인증 토큰을 생성한다.")
  @Test
  void createAuthTokenWhenRefreshTokenNotValid() {
    Token token = Token.create(userId, refreshToken);
    String newRefreshToken = "newRefreshToken";

    when(jwtTokenProvider.createAccessToken(userId)).thenReturn(accessToken);
    when(jwtTokenProvider.createRefreshToken(userId)).thenReturn(newRefreshToken);
    when(jwtTokenProvider.isExpired(token.getRefreshToken())).thenReturn(true);
    when(tokenRepository.findTokenByUserId(userId)).thenReturn(Optional.of(token));

    AuthTokenDto authToken = sut.createAuthToken(userId);

    assertEquals(newRefreshToken, authToken.getRefreshToken());
  }

  @DisplayName("유효한 리프레시 토큰으로 액세스 토큰 갱신한다.")
  @Test
  void refreshAccessTokenByValidRefreshToken() {
    when(jwtTokenProvider.getPayload(refreshToken)).thenReturn(userId);
    when(jwtTokenProvider.createAccessToken(userId)).thenReturn(accessToken);

    AuthTokenDto authTokenDto = sut.refreshAccessToken(refreshToken);

    assertEquals(accessToken, authTokenDto.getAccessToken());
  }
}