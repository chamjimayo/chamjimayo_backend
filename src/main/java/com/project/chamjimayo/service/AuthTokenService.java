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
    String refreshToken = getRefreshToken(userId);

    return AuthTokenDto.create(accessToken, refreshToken);
  }

  public AuthTokenDto refreshAccessToken(final String refreshToken) {
    String userId = jwtTokenProvider.getPayload(refreshToken);
    String accessTokenForRenew = jwtTokenProvider.createAccessToken(userId);

    return AuthTokenDto.create(accessTokenForRenew, refreshToken);
  }

  private String getRefreshToken(String userId) {
    Token token = tokenRepository.findTokenByUserId(userId)
        .orElse(null);

    if (isNotValid(token)) {
      String refreshToken = jwtTokenProvider.createRefreshToken(userId);

      if (token == null) {
        token = tokenRepository.save(Token.create(userId, refreshToken));
      } else {
        token.changeRefreshToken(refreshToken);
      }
    }

    return token.getRefreshToken();
  }

  private boolean isNotValid(Token token) {
    if (token == null) {
      return true;
    }

    return !validateToken(token.getRefreshToken());
  }

  public String extractPayload(final String accessToken) {
    return jwtTokenProvider.getPayload(accessToken);
  }

  public boolean validateToken(final String token) {
    return jwtTokenProvider.isValid(token);
  }
}
