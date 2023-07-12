package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.AuthTokenResponse;
import com.project.chamjimayo.controller.dto.IssueTokenRequest;
import com.project.chamjimayo.controller.dto.LoginRequest;
import com.project.chamjimayo.controller.dto.SignUpRequest;
import com.project.chamjimayo.security.dto.AuthTokenDto;
import com.project.chamjimayo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<AuthTokenResponse> signUp(@RequestBody SignUpRequest request) {
    AuthTokenDto dto = authService.issueNewToken(request.toDto());
    return ResponseEntity.ok(dto.toResponse());
  }

  @PostMapping("/login")
  public ResponseEntity<AuthTokenResponse> login(@RequestBody LoginRequest request) {
    AuthTokenDto dto = authService.issueToken(request.getAuthId());
    return ResponseEntity.ok(dto.toResponse());
  }

  @PostMapping("/token/access")
  public ResponseEntity<AuthTokenResponse> issueToken(
      @RequestBody IssueTokenRequest issueTokenRequest) {
    AuthTokenDto dto = authService.refreshToken(issueTokenRequest.toDto());
    return ResponseEntity.ok(dto.toResponse());
  }
}
