package com.project.chamjimayo.security;

import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.security.dto.AuthTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenFactory {

  private final JwtTokenProvider jwtTokenProvider;

  public AuthTokenDto createAuthToken(final String userId) {
    jwtTokenProvider.validateToken(userId);

    String accessToken = jwtTokenProvider.createAccessToken(userId);
    String refreshToken = jwtTokenProvider.createRefreshToken(userId);

    return AuthTokenDto.create(accessToken, refreshToken);
  }

  public AuthTokenDto refreshAccessToken(final String refreshToken) {
    jwtTokenProvider.validateToken(refreshToken);

    String userId = jwtTokenProvider.getPayload(refreshToken);

    String accessTokenForRenew = jwtTokenProvider.createAccessToken(userId);
    String refreshTokenForRenew = jwtTokenProvider.getRefreshToken(userId);

    isSame(refreshToken, refreshTokenForRenew);
    return AuthTokenDto.create(accessTokenForRenew, refreshTokenForRenew);
  }

  private void isSame(String refreshToken, String refreshTokenForRenew) {
    if (!refreshToken.equals(refreshTokenForRenew)) {
      throw new AuthException("토큰이 다릅니다.");
    }
  }

  public String extractPayload(final String accessToken) {
    jwtTokenProvider.validateToken(accessToken);
    return jwtTokenProvider.getPayload(accessToken);
  }

  public boolean validateToken(final String token) {
    return jwtTokenProvider.validateToken(token);
  }
}
