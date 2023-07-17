package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.dto.RestroomSummaryDto;
import com.project.chamjimayo.service.dto.RestroomSummaryDto.Response;
import com.project.chamjimayo.service.dto.UserDetailsDto;
import com.project.chamjimayo.service.UserService;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
  public ResponseEntity<ApiStandardResponse<DuplicateCheckDto.Response>> nicknameCheckDuplication(
      @PathVariable("nickname") String nickname) {
    DuplicateCheckDto dto = userService.isNicknameDuplicate(nickname);
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }

  @Operation(summary = "사용자 정보 조회", description = "Jwt 토큰으로 요청 후 해당 사용자 정보 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
      @ApiResponse(responseCode = "400", description = "1. 사용자가 존재하지 않습니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
                  + "\"msg\":\"사용자가 존재하지 않습니다\"} }")
          )
      )
  })
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
  in = ParameterIn.HEADER, example = "Bearer e1323423534")
  @GetMapping("/me")
  public ResponseEntity<ApiStandardResponse<UserDetailsDto.Response>> userDetails(
      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    UserDetailsDto dto = userService.getUserDetails(customUserDetails.getId());
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }

  @Operation(summary = "사용중인 화장실 조회",
      description = "Jwt 토큰으로 요청 후 해당 사용자의 사용 중인 화장실 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "사용 중인 화장실 정보 조회 성공")
  })
  @GetMapping("/me/using-restroom")
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534")
  public ResponseEntity<ApiStandardResponse<RestroomSummaryDto.Response>> usingRestroomSummary(
      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
    RestroomSummaryDto dto = userService.getUsingRestroom(customUserDetails.getId());
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }

  @Operation(summary = "화장실 사용 내역 조회",
      description = "Jwt 토큰으로 요청 후 해당 사용자의 화장실 사용 내역 반환")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로그인 성공")
  })
  @GetMapping("/me/used-restrooms")
  @Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
      in = ParameterIn.HEADER, example = "Bearer e1323423534")
  public ResponseEntity<ApiStandardResponse<Page<RestroomSummaryDto.Response>>> usedRestroomsSummary(
      @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PageableDefault(size = 20, page = 0) Pageable pageable) {
    Page<Response> responses= userService
        .getUsedRestrooms(customUserDetails.getId(), pageable)
        .map(RestroomSummaryDto::toResponse);
    return ResponseEntity.ok(ApiStandardResponse.success(responses));
  }
}
