package com.project.chamjimayo.service;

import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.service.dto.AuthTokenDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthTokenService authTokenService;
  private final UserService userService;

  public AuthTokenDto issueNewToken(SignUpDto dto) {
    String id = userService.saveUser(dto);
    return authTokenService.createAuthToken(id);
  }

  public AuthTokenDto issueToken(String authId) {
    String id = userService.getUserByAuthId(authId);
    return authTokenService.createAuthToken(id);
  }

  public AuthTokenDto refreshToken(String refreshToken) {
    if (!authTokenService.has(refreshToken)) {
      throw new InvalidTokenException("유효한 refresh token이 아닙니다.");
    }

    if (authTokenService.isExpired(refreshToken)) {
      String userId = authTokenService.extractPayload(refreshToken);

      return authTokenService.createAuthToken(userId);
    }

    return authTokenService.refreshAccessToken(refreshToken);
  }
}
