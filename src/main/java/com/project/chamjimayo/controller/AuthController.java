package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.LoginRequest;
import com.project.chamjimayo.controller.dto.SignUpRequest;
import com.project.chamjimayo.service.AuthService;
import com.project.chamjimayo.service.dto.AuthTokenDto;
import com.project.chamjimayo.service.dto.AuthTokenDto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
      @ApiResponse(responseCode = "400", description = "1. 사용자 닉네임이 중복됩니다. \t\n"
          + "2. 사용자 식별 아이디가 이미 존재합니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"03\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"USER_DUPLICATE_EXCEPTION\", "
                  + "\"msg\":\"사용자가 이미 존재합니다.\"} }")
          )
      )
  })
  @PostMapping("/signup")
  public ResponseEntity<ApiStandardResponse<Response>> signUp(
      @RequestBody SignUpRequest request) {
    AuthTokenDto dto = authService.issueNewToken(request.toDto());
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }

  @Operation(summary = "로그인", description = "회원 식별 번호 요청 후 access, refresh token 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로그인 성공"),
      @ApiResponse(responseCode = "400", description = "1. 사용자가 존재하지 않습니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
                  + "\"msg\":\"사용자가 존재하지 않습니다\"} }")
          )
      )
  })
  @PostMapping("/login")
  public ResponseEntity<ApiStandardResponse<Response>> login(
      @RequestBody LoginRequest request) {
    AuthTokenDto dto = authService.issueToken(request.getAuthId());
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }

  @Operation(summary = "액세스 토큰 갱신",
      description = "refresh token으로 access 토큰 갱신")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "액세스 토큰 갱신 성공"),
      @ApiResponse(responseCode = "400", description = "1. 토큰이 유효하지 않습니다. \t\n"
          + "2. 사용자가 존재하지 않습니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"06\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"INVALID_TOKEN_EXCEPTION\", "
                  + "\"msg\":\"토큰이 유효하지 않습니다\"} }")
          )
      )
  })
  @PostMapping("/token/access")
  public ResponseEntity<ApiStandardResponse<Response>> issueToken(
      @RequestBody String refreshToken) {
    AuthTokenDto dto = authService.refreshToken(refreshToken);
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }
}
