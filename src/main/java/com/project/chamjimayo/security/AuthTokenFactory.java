package com.project.chamjimayo.security;

import com.project.chamjimayo.domain.entity.Token;
import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.repository.TokenRepository;
import com.project.chamjimayo.security.dto.AuthTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenFactory {

  private final JwtTokenProvider jwtTokenProvider;
  private final TokenRepository tokenRepository;

  public AuthTokenDto createAuthToken(final String userId) {
    String accessToken = jwtTokenProvider.createAccessToken(userId);
    String refreshToken = getRefreshToken(userId);

    return AuthTokenDto.create(accessToken, refreshToken);
  }

  private String getRefreshToken(final String userId) {
    Token token = tokenRepository.findTokenByUserId(userId)
        .orElse(null);

    if (isNotValid(token)) {
      String refreshToken = jwtTokenProvider.createRefreshToken(userId);
      Token savedToken = tokenRepository.save(Token.create(userId, refreshToken));
      return savedToken.getRefreshToken();
    }

    return token.getRefreshToken();
  }

  private boolean isNotValid(Token token) {
    return token == null || !validateToken(token.getRefreshToken());
  }

  public AuthTokenDto refreshAccessToken(final String refreshToken) {
    String userId = jwtTokenProvider.getPayload(refreshToken);

    String accessTokenForRenew = jwtTokenProvider.createAccessToken(userId);
    String refreshTokenForRenew = getRefreshToken(userId);

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
