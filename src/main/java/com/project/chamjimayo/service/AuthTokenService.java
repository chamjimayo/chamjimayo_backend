package com.project.chamjimayo.service;

import com.project.chamjimayo.domain.entity.Token;
import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.repository.TokenRepository;
import com.project.chamjimayo.security.JwtTokenProvider;
import com.project.chamjimayo.service.dto.AuthTokenDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class AuthTokenService {

  private final JwtTokenProvider jwtTokenProvider;
  private final TokenRepository tokenRepository;

  public AuthTokenDto createAuthToken(final String userId) {
    String accessToken = jwtTokenProvider.createAccessToken(userId);
    String refreshToken;

    Token token = tokenRepository.findTokenByUserId(userId)
        .orElse(null);

    if (isNotValid(token)) {
      String refreshToken1 = jwtTokenProvider.createRefreshToken(userId);

      if (token == null) {
        token = tokenRepository.save(Token.create(userId, refreshToken1));
      } else {
        token.changeRefreshToken(refreshToken1);
      }
    }
    refreshToken = token.getRefreshToken();

    return AuthTokenDto.create(accessToken, refreshToken);
  }

  private boolean isNotValid(Token token) {
    if (token != null) {
      return !validateToken(token.getRefreshToken());
    }
    return true;
  }

  public AuthTokenDto refreshAccessToken(final String refreshToken) {
    String userId = jwtTokenProvider.getPayload(refreshToken);

    String accessTokenForRenew = jwtTokenProvider.createAccessToken(userId);
    String refreshTokenForRenew;

    Token token = tokenRepository.findTokenByUserId(userId)
        .orElse(null);

    if (isNotValid(token)) {
      String refreshToken1 = jwtTokenProvider.createRefreshToken(userId);

      if (token == null) {
        token = tokenRepository.save(Token.create(userId, refreshToken1));
      } else {
        token.changeRefreshToken(refreshToken1);
      }
    }
    refreshTokenForRenew = token.getRefreshToken();

    isSame(refreshToken, refreshTokenForRenew);
    return AuthTokenDto.create(accessTokenForRenew, refreshTokenForRenew);
  }

  private void isSame(String refreshToken, String refreshTokenForRenew) {
    if (!refreshToken.equals(refreshTokenForRenew)) {
      throw new InvalidTokenException("토큰이 유효하지 않습니다.");
    }
  }

  public String extractPayload(final String accessToken) {
    return jwtTokenProvider.getPayload(accessToken);
  }

  public boolean validateToken(final String token) {
    return jwtTokenProvider.isValid(token);
  }
}
