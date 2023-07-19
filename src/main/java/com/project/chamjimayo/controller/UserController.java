package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.PointChangeDto;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.UserService;
import com.project.chamjimayo.service.dto.DuplicateCheckDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		return ResponseEntity.ok(
			ApiStandardResponse.success(userService.isNicknameDuplicate(nickname)));
	}

	@Operation(summary = "포인트 충전", description = "해당 유저의 포인트를 충전합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "포인트 충전 성공."),
		@ApiResponse(responseCode = "403",
			description = "1. 권한이 없습니다. \t\n",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"05\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"AUTH_EXCEPTION\", "
					+ "\"msg\":\"권한이 없습니다.\"} }"))),
		@ApiResponse(responseCode = "404",
			description = "1. 유저를 찾지 못했습니다. \t\n",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"8\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
					+ "\"msg\":\"유저를 찾지 못했습니다.\"} }")))})
	@Parameters({
		@Parameter(in = ParameterIn.HEADER, name = "Bearer-Token", required = true)
	})
	@PostMapping("/point/charge")
	public ResponseEntity<ApiStandardResponse<PointChangeDto>> chargePoints(
		@RequestBody PointChangeDto requestDTO,
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		if (requestDTO.getUserId().equals(customUserDetails.getId())) {
			PointChangeDto responseDTO = userService.chargePoints(requestDTO);
			return ResponseEntity.ok(ApiStandardResponse.success(responseDTO));
		} else {
			throw new AuthException("권한이 없습니다.");
		}
	}

	@Operation(summary = "포인트 차감", description = "해당 유저의 포인트를 차감합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "포인트 차감 성공."),
		@ApiResponse(responseCode = "400",
			description = "1. 포인트가 부족합니다. \t\n",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"22\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"POINT_NOT_ENOUGH\", "
					+ "\"msg\":\"포인트가 부족합니다.\"} }"))),
		@ApiResponse(responseCode = "403",
			description = "1. 권한이 없습니다. \t\n",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"05\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"AUTH_EXCEPTION\", "
					+ "\"msg\":\"권한이 없습니다.\"} }"))),
		@ApiResponse(responseCode = "404",
			description = "1. 유저를 찾지 못했습니다. \t\n",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"8\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
					+ "\"msg\":\"유저를 찾지 못했습니다.\"} }")))})
	@Parameters({
		@Parameter(in = ParameterIn.HEADER, name = "Bearer-Token", required = true)
	})
	@PostMapping("/point/deduct")
	public ResponseEntity<ApiStandardResponse<PointChangeDto>> deductPoints(
		@RequestBody PointChangeDto requestDTO,
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		if (requestDTO.getUserId().equals(customUserDetails.getId())) {
			PointChangeDto responseDTO = userService.deductPoints(requestDTO);
			return ResponseEntity.ok(ApiStandardResponse.success(responseDTO));
		} else {
			throw new AuthException("권한이 없습니다.");
		}
	}
}
