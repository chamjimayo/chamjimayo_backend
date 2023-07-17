package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.service.UserService;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "user", description = "유저 Api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "닉네임 검증", description = "주어진 닉네임이 중복됐는지 검증")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "중복 체크 성공")
  })
  @GetMapping("/check-nickname/{nickname}")
  public ResponseEntity<ApiStandardResponse<DuplicateCheckDto>> nicknameCheckDuplication(
      @PathVariable("nickname") String nickname) {
    return ResponseEntity.ok(ApiStandardResponse.success(userService.isNicknameDuplicate(nickname)));
  }
}
