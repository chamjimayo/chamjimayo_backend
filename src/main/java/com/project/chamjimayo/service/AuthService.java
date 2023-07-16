package com.project.chamjimayo.service;

import com.project.chamjimayo.service.dto.AuthTokenDto;
import com.project.chamjimayo.service.dto.IssueTokenDto;
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

  public AuthTokenDto refreshToken(IssueTokenDto dto) {
    if (authTokenService.validateToken(dto.getRefreshToken())) {
      return authTokenService.refreshAccessToken(dto.getRefreshToken());
    }

    String userId = userService.getUserByAuthId(dto.getAuthId());

    return authTokenService.createAuthToken(userId);
  }
}
