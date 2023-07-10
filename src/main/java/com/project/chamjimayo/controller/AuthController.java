package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.AuthTokenResponse;
import com.project.chamjimayo.controller.dto.SignUpRequest;
import com.project.chamjimayo.domain.entity.AuthType;
import com.project.chamjimayo.service.UserService;
import com.project.chamjimayo.service.dto.AuthTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AuthController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<AuthTokenResponse> signUp(@RequestBody SignUpRequest request) {
    AuthTokenDto dto = userService.saveUser(request.toDto());
    return ResponseEntity.ok(dto.toResponse());
  }
}
