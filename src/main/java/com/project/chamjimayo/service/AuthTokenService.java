package com.project.chamjimayo.service;

import com.project.chamjimayo.repository.domain.entity.Token;
import com.project.chamjimayo.repository.TokenRepository;
import com.project.chamjimayo.security.JwtTokenProvider;
import com.project.chamjimayo.security.config.JwtProperties;
import com.project.chamjimayo.service.dto.AuthTokenDto;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenService {

  private final JwtTokenProvider jwtTokenProvider;
  private final TokenRepository tokenRepository;
  private final long accessTokenValidityMs;
  private final long refreshTokenValidityMs;

  public AuthTokenService(JwtTokenProvider jwtTokenProvider, TokenRepository tokenRepository,
      JwtProperties jwtProperties) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.tokenRepository = tokenRepository;
    this.accessTokenValidityMs = jwtProperties.getAccessTokenValidityMs();
    this.refreshTokenValidityMs = jwtProperties.getRefreshTokenValidityMs();
  }

  @Transactional
  public AuthTokenDto createAuthToken(final String userId) {
    String accessToken = jwtTokenProvider.createAccessToken(userId);
    String refreshToken = getRefreshToken(userId);

    return AuthTokenDto.create(accessToken, refreshToken,
        accessTokenValidityMs, refreshTokenValidityMs);
  }

  public AuthTokenDto refreshAccessToken(final String refreshToken) {
    String userId = jwtTokenProvider.getPayload(refreshToken);
    String accessTokenForRenew = jwtTokenProvider.createAccessToken(userId);

    return AuthTokenDto.create(accessTokenForRenew, refreshToken,
        accessTokenValidityMs, refreshTokenValidityMs);
  }

  private String getRefreshToken(String userId) {
    Token token = tokenRepository.findTokenByUserId(userId).orElse(null);

    if (token == null) {
      String refreshToken = jwtTokenProvider.createRefreshToken(userId);
      token = tokenRepository.save(Token.create(userId, refreshToken));
    }

    if (jwtTokenProvider.isExpired(token.getRefreshToken())) {
      String refreshToken = jwtTokenProvider.createRefreshToken(userId);
      token.changeRefreshToken(refreshToken);
    }

    return token.getRefreshToken();
  }

  public String extractPayload(final String token) {
    return jwtTokenProvider.getPayload(token);
  }

  public boolean isValid(final String token) {
    return jwtTokenProvider.isValid(token);
  }

  public boolean isExpired(final String token) {
    return jwtTokenProvider.isExpired(token);
  }

  public boolean has(String refreshToken) {
    return tokenRepository.existsTokenByRefreshToken(refreshToken);
  }
}
