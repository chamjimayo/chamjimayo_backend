package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.AuthTokenResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.IssueTokenRequest;
import com.project.chamjimayo.controller.dto.LoginRequest;
import com.project.chamjimayo.controller.dto.SignUpRequest;
import com.project.chamjimayo.security.dto.AuthTokenDto;
import com.project.chamjimayo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth", description = "인증 API")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "회원가입", description = "회원가입 포맷으로 회원가입 후 access, refresh token 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "회원가입 성공"),
      @ApiResponse(responseCode = "400", description = "요청 변수 에러",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/signup")
  public ResponseEntity<AuthTokenResponse> signUp(@RequestBody SignUpRequest request) {
    AuthTokenDto dto = authService.issueNewToken(request.toDto());
    return ResponseEntity.ok(dto.toResponse());
  }

  @Operation(summary = "로그인", description = "회원 식별 번호 요청 후 access, refresh token 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로그인 성공"),
      @ApiResponse(responseCode = "400", description = "요청 변수 에러",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/login")
  public ResponseEntity<AuthTokenResponse> login(@RequestBody LoginRequest request) {
    AuthTokenDto dto = authService.issueToken(request.getAuthId());
    return ResponseEntity.ok(dto.toResponse());
  }

  @Operation(summary = "액세스 토큰 갱신", description = "회원 식별 번호와 refresh token으로 access 토큰 갱신")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "액세스 토큰 갱신 성공"),
      @ApiResponse(responseCode = "400", description = "요청 변수 에러",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/token/access")
  public ResponseEntity<AuthTokenResponse> issueToken(
      @RequestBody IssueTokenRequest issueTokenRequest) {
    AuthTokenDto dto = authService.refreshToken(issueTokenRequest.toDto());
    return ResponseEntity.ok(dto.toResponse());
  }
}
