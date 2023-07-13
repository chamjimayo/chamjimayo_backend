package com.project.chamjimayo.service;

import com.project.chamjimayo.security.AuthTokenFactory;
import com.project.chamjimayo.security.dto.AuthTokenDto;
import com.project.chamjimayo.service.dto.IssueTokenDto;
import com.project.chamjimayo.service.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  
  private final AuthTokenFactory authTokenFactory;
  private final UserService userService;
  
  public AuthTokenDto issueNewToken(SignUpDto dto) {
    String id = userService.saveUser(dto);
    return authTokenFactory.createAuthToken(id);
  }

  public AuthTokenDto issueToken(String authId) {
    String id = userService.getUserByAuthId(authId);
    return authTokenFactory.createAuthToken(id);
  }

  public AuthTokenDto refreshToken(IssueTokenDto dto) {
    if (authTokenFactory.validateToken(dto.getRefreshToken())) {
      return authTokenFactory.refreshAccessToken(dto.getRefreshToken());
    }

    String userId = userService.getUserByAuthId(dto.getAuthId());

    return authTokenFactory.createAuthToken(userId);
  }
}
