package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.request.PointRequest;
import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.response.DuplicateCheckResponse;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.controller.dto.response.PointResponse;
import com.project.chamjimayo.controller.dto.response.RestroomSummaryResponse;
import com.project.chamjimayo.controller.dto.response.UserDetailsResponse;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.UserService;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import com.project.chamjimayo.service.dto.PointDto;
import com.project.chamjimayo.service.dto.RestroomSummaryDto;
import com.project.chamjimayo.service.dto.UserDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<ApiStandardResponse<DuplicateCheckResponse>> nicknameCheckDuplication(
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
		in = ParameterIn.HEADER, example = "Bearer e1323423534", required = true)
	@GetMapping("/me")
	public ResponseEntity<ApiStandardResponse<UserDetailsResponse>> userDetails(
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
		in = ParameterIn.HEADER, example = "Bearer e1323423534", required = true)
	public ResponseEntity<ApiStandardResponse<RestroomSummaryResponse>> usingRestroomSummary(
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		RestroomSummaryDto dto = userService.getUsingRestroom(customUserDetails.getId());
		return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
	}

	@Operation(summary = "화장실 사용 내역 조회",
		description = "Jwt 토큰으로 요청 후 해당 사용자의 화장실 사용 내역 반환")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "화장실 사용 내역 조회 성공")
	})
	@GetMapping("/me/used-restrooms")
	@Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "Bearer e1323423534", required = true)
	public ResponseEntity<ApiStandardResponse<Page<RestroomSummaryResponse>>> usedRestroomsSummary(
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails,
			@RequestParam int page, @RequestParam int size) {
		Page<RestroomSummaryResponse> responses = userService
			.getUsedRestrooms(customUserDetails.getId(), PageRequest.of(page, size))
			.map(RestroomSummaryDto::toResponse);
		return ResponseEntity.ok(ApiStandardResponse.success(responses));
	}

  @Operation(summary = "포인트 충전", description = "해당 유저의 포인트를 충전합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "포인트 충전 성공."),
      @ApiResponse(responseCode = "400",
          description = "1. 유저 ID를 입력해주세요. \t\n"
              + "3. 포인트를 입력해주세요. \t\n"
              + "4. 포인트의 최솟값은 0입니다. \t\n"
              + "5. 올바르지 않은 JSON 형식입니다. \t\n"
              + "6. 유효한 토큰이 아닙니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
                  + "\"msg\":\"유저를 찾지 못했습니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 유저를 찾지 못했습니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
                  + "\"msg\":\"유저를 찾지 못했습니다.\"} }")))})
  @PostMapping("/point/charge")
  public ResponseEntity<ApiStandardResponse<PointResponse>> chargePoints(
      @Valid @RequestBody PointRequest pointRequest) {
    PointDto dto = userService.chargePoints(PointDto.create(pointRequest));
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }

  @Operation(summary = "포인트 차감", description = "해당 유저의 포인트를 차감합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "포인트 차감 성공."),
      @ApiResponse(responseCode = "400",
          description = "1. 포인트가 부족합니다."
              + "2. 유저 ID를 입력해주세요. \t\n"
              + "3. 포인트를 입력해주세요. \t\n"
              + "4. 포인트의 최솟값은 0입니다. \t\n"
              + "5. 올바르지 않은 JSON 형식입니다. \t\n"
              + "6. 유효한 토큰이 아닙니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"22\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"POINT_NOT_ENOUGH\", "
                  + "\"msg\":\"포인트가 부족합니다.\"} }"))),
      @ApiResponse(responseCode = "404",
          description = "1. 유저를 찾지 못했습니다.",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class),
              examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
                  + " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
                  + "\"msg\":\"유저를 찾지 못했습니다.\"} }")))})
  @PostMapping("/point/deduct")
  public ResponseEntity<ApiStandardResponse<PointResponse>> deductPoints(
      @Valid @RequestBody PointRequest pointRequest) {
    PointDto dto = userService.deductPoints(PointDto.create(pointRequest));
    return ResponseEntity.ok(ApiStandardResponse.success(dto.toResponse()));
  }
}
