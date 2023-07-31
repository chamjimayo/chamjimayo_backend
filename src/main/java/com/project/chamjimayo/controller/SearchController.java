package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.SearchResponseDto;
import com.project.chamjimayo.domain.entity.Search;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.AuthException;
import com.project.chamjimayo.exception.SearchHistoryNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.SearchRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.SearchService;
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
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "search_address", description = "주소 검색 API")
@RequiredArgsConstructor
@RequestMapping("/api/address")
@RestController
@Validated
public class SearchController {

	private final SearchService searchService;
	private final SearchRepository searchRepository;
	private final UserJpaRepository userJpaRepository;

	/**
	 * 검색어와 유저 id를 받아서 검색어에 대한 도로명 주소, 지번 주소, 가게 이름을 반환 searchAddress 의 count 변수로 조절 가능 예시:
	 * /address/search?searchWord={검색어}
	 */
	@Operation(summary = "검색", description = "검색어를 받고 검색 결과를 제공합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 결과 반환"),
		@ApiResponse(responseCode = "400",
			description = "1. 유효한 토큰이 아닙니다. \t\n"
				+ "2. 파라미터가 부족합니다. \t\n"
				+ "3. 올바르지 않은 파라미터 값입니다. \t\n"
				+ "4. 검색어를 입력해주세요. \t\n"
				+ "5. 검색어에는 특수문자를 포함할 수 없습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"06\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"INVALID_TOKEN_EXCEPTION\", "
					+ "\"msg\":\"유효한 토큰이 아닙니다.\"} }"))),
		@ApiResponse(responseCode = "404",
			description = "1. Api 응답이 올바르지 않습니다. \t\n"
				+ "2. 유저를 찾지 못했습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"10\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"API_NOT_FOUND\", "
					+ "\"msg\":\"Api 응답이 올바르지 않습니다.\"} }")))})
	@Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "Bearer e1323423534")
	@GetMapping("/search")
	public ResponseEntity<ApiStandardResponse<List<SearchResponseDto>>> getAddress(
		@Parameter(description = "검색어", required = true, example = "스타벅스")
		@NotBlank(message = "검색어를 입력해주세요.")
		@Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]*$", message = "검색어에는 특수문자를 포함할 수 없습니다.")
		@RequestParam("searchWord") String searchWord,
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 현재 로그인 한 유저의 userId를 가져옴
		Long userId = customUserDetails.getId();
		List<SearchResponseDto> searchResponseDTOList = searchService.searchAddress(searchWord,
			userId);
		ApiStandardResponse<List<SearchResponseDto>> apiStandardResponse = ApiStandardResponse.success(
			searchResponseDTOList);
		return ResponseEntity.ok(apiStandardResponse);
	}

	/**
	 * 유저 아이디를 받아서 해당 유저의 최근 검색 기록 리스트 반환 예시: /address/search/recent
	 */
	@Operation(summary = "해당 유저의 최근 검색 기록 리스트 반환", description = "해당 유저의 최근 검색 기록들을 반환합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "최근 검색 기록 반환"),
		@ApiResponse(responseCode = "400", description = "1. 유효한 토큰이 아닙니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"06\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"INVALID_TOKEN_EXCEPTION\", "
					+ "\"msg\":\"유효한 토큰이 아닙니다.\"} }"))),
		@ApiResponse(responseCode = "404", description = "1. 유저를 찾지 못했습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
					+ "\"msg\":\"유저를 찾지 못했습니다.\"} }")))})
	@Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "Bearer e1323423534")
	@GetMapping("/search/recent")
	public ResponseEntity<ApiStandardResponse<List<SearchResponseDto>>> getUserSearchHistory(
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 현재 로그인 한 유저의 userId를 가져옴
		Long userId = customUserDetails.getId();
		// userId를 통해 최근 검색 기록을 가져옴
		List<SearchResponseDto> searchResponseDTOList = searchService.getUserSearchHistory(userId);
		ApiStandardResponse<List<SearchResponseDto>> apistandardresponse = ApiStandardResponse.success(
			searchResponseDTOList);
		return ResponseEntity.ok(apistandardresponse);
	}

	/**
	 * 도로명 주소를 클릭한 경우 해당 도로명 주소의 상태를 변경 -> 해당 주소를 클릭 처리하면 최종적으로 검색한 것으로 처리 후 저장
	 */
	@Operation(summary = "주소 클릭", description = "검색 기록을 클릭(저장)합니다.(이미 존재하는 경우, 최신화 적용)")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "정상적으로 클릭이 되었습니다."),
		@ApiResponse(responseCode = "400",
			description = "1. 파라미터가 부족합니다. \t\n"
				+ "2. 올바르지 않은 파라미터 값입니다. \t\n"
				+ "3. 검색어를 입력해주세요. \t\n"
				+ "4. 도로명 주소를 입력해주세요. \t\n"
				+ "5. 지번 주소를 입력해주세요. \t\n"
				+ "6. 가게 이름을 입력해주세요. \t\n"
				+ "7. 위도를 입력해주세요. \t\n"
				+ "8. 경도를 입력해주세요. \t\n"
				+ "9. 위도는 -90 ~ 90으로 입력해주세요. \t\n"
				+ "10. 경도는 -180 ~ 180으로 입력해주세요. \t\n"
				+ "11. 올바르지 않은 JSON 형식입니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
					+ "\"msg\":\"파라미터가 부족합니다.\"} }"))),
		@ApiResponse(responseCode = "404",
			description = "1. 검색 기록을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"09\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"SEARCH_NOT_FOUND\", "
					+ "\"msg\":\"검색 기록을 찾을 수 없습니다.\"} }")))})
	@Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "Bearer e1323423534")
	@PostMapping("/search/click")
	public ResponseEntity<ApiStandardResponse<String>> clickAddress(
		@Valid @RequestBody SearchResponseDto searchResponseDto,
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		searchService.clickAddress(customUserDetails.getId(), searchResponseDto);
		ApiStandardResponse<String> apiStandardResponse = ApiStandardResponse.success(
			"정상적으로 클릭이 되었습니다.");
		return ResponseEntity.ok(apiStandardResponse);
	}

	/**
	 * 가게 이름을 통해서 검색 기록 삭제
	 */
	@Operation(summary = "해당 유저의 특정 검색 기록 삭제", description = "해당 유저의 특정 검색 기록을 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 기록 삭제 성공"),
		@ApiResponse(responseCode = "400",
			description = "1. 파라미터가 부족합니다. \t\n"
				+ "2. 올바르지 않은 파라미터 값입니다. \t\n"
				+ "3. 가게 이름을 입력해주세요. \t\n"
				+ "4. 가게 이름에는 특수문자를 포함할 수 없습니다. \t\n"
				+ "5. 유효한 토큰이 아닙니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"02\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"NEED_MORE_PARAMETER\", "
					+ "\"msg\":\"파라미터가 부족합니다.\"} }"))),
		@ApiResponse(responseCode = "403",
			description = "1. 권한이 없습니다. \t\n",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"05\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"AUTH_EXCEPTION\", "
					+ "\"msg\":\"권한이 없습니다.\"} }"))),
		@ApiResponse(responseCode = "404",
			description = "1. 검색 기록을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"09\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"SEARCH_NOT_FOUND\", "
					+ "\"msg\":\"검색 기록을 찾을 수 없습니다.\"} }")))})
	@Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "Bearer e1323423534")
	@DeleteMapping("/search")
	public ResponseEntity<ApiStandardResponse<String>> deleteRecentSearchHistory(
		@Parameter(description = "가게 이름", required = true, example = "스타벅스 서울역점")
		@NotBlank(message = "가게 이름을 입력해주세요.")
		@Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]*$", message = "가게 이름에는 특수문자를 포함할 수 없습니다.")
		@RequestParam("name") String name,
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		Search existingSearch = searchRepository.findByName(name)
			.orElseThrow(() -> new SearchHistoryNotFoundException("검색 기록을 찾을 수 없습니다."));
		if (existingSearch.getUser().getUserId().equals(customUserDetails.getId())) {
			searchService.deleteRecentSearchHistory(existingSearch);
			ApiStandardResponse<String> apiStandardResponse = ApiStandardResponse.success(
				"검색 기록 삭제 성공");
			return ResponseEntity.ok(apiStandardResponse);
		} else {
			throw new AuthException("권한이 없습니다.");
		}
	}

	@Operation(summary = "해당 유저의 모든 검색 기록 삭제", description = "해당 유저의 모든 검색 기록을 삭제합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모든 검색 기록 삭제 성공"),
		@ApiResponse(responseCode = "400", description = "1. 유효한 토큰이 아닙니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"06\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"INVALID_TOKEN_EXCEPTION\", "
					+ "\"msg\":\"유효한 토큰이 아닙니다.\"} }"))),
		@ApiResponse(responseCode = "404", description = "1. 유저를 찾지 못했습니다.",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = ErrorResponse.class),
				examples = @ExampleObject(value = "{ \"code\": \"08\", \"msg\": \"fail\","
					+ " \"data\": {\"status\": \"USER_NOT_FOUND_EXCEPTION\", "
					+ "\"msg\":\"유저를 찾지 못했습니다.\"} }")))})
	@Parameter(name = "Bearer-Token", description = "jwt token", schema = @Schema(type = "string"),
		in = ParameterIn.HEADER, example = "Bearer e1323423534")
	@DeleteMapping("/search/all")
	public ResponseEntity<ApiStandardResponse<String>> deleteRecentSearchHistoryAll(
		@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails customUserDetails) {
		Long userId = customUserDetails.getId();
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다. ID: " + userId));
		searchService.deleteRecentSearchHistoryAll(user);
		ApiStandardResponse<String> apiStandardResponse = ApiStandardResponse.success(
			"모든 검색 기록 삭제 성공");
		return ResponseEntity.ok(apiStandardResponse);
	}
}


