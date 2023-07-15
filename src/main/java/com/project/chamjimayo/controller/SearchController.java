package com.project.chamjimayo.controller;

import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.controller.dto.SearchRequestDto;
import com.project.chamjimayo.controller.dto.SearchResponseDto;
import com.project.chamjimayo.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "search_address", description = "주소 검색 API")
@RequiredArgsConstructor
@RequestMapping("/address")
@RestController
public class SearchController {

	private final SearchService searchService;

	/**
	 * 검색어와 유저 id를 받아서 검색어에 대한 도로명 주소, 지번 주소, 가게 이름을 반환 searchAddress 의 count 변수로 조절 가능
	 * 예시: /address/search?searchWord={검색어}
	 */
	@Operation(summary = "검색", description = "검색어를 받고 검색 결과를 제공합니다.")
	@GetMapping("/search")
	public ResponseEntity<List<SearchResponseDto>> getAddress(
		@Parameter(description = "검색어", required = true, example = "스타벅스신림역")
		@RequestParam("searchWord") String searchWord,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 현재 로그인 한 유저의 userId를 가져옴
		Long userId = customUserDetails.getId();
		SearchRequestDto requestDTO = SearchRequestDto.create(searchWord, userId);
		List<SearchResponseDto> searchResponseDTOList = searchService.searchAddress(requestDTO);
		return ResponseEntity.ok(searchResponseDTOList);
	}


	/**
	 * 유저 아이디를 받아서 해당 유저의 최근 검색 기록 (도로명 주소, 지번 주소, 이름)
	 * 예시: /address/search/recent
	 */
	@Operation(summary = "최근 검색 기록", description = "최근 검색 기록을 반환합니다.")
	@GetMapping("/search/recent")
	public ResponseEntity<SearchResponseDto> getRecentAddress(
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 현재 로그인 한 유저의 userId를 가져옴
		Long userId = customUserDetails.getId();
		// userId를 통해 최근 검색 기록을 가져옴
		SearchResponseDto responseDTO = searchService.getRecentRoadAddress(Long.valueOf(userId));
		// 검색 결과를 ResponseEntity.ok() 메서드를 사용하여 성공적인 응답으로 반환
		return ResponseEntity.ok(responseDTO);
	}

	/**
	 * 도로명 주소를 클릭한 경우 해당 도로명 주소의 상태를 변경 -> 해당 주소를 클릭 처리하면 최종적으로 검색한 것으로 처리 예시:
	 * /address/search/click/{searchId}
	 */
	@Operation(summary = "클릭", description = "검색 기록을 클릭 처리합니다.(최근 검색 기록을 반환하기 위한 처리)")
	@PostMapping("/search/click/{searchId}")
	public ResponseEntity<String> clickAddress(
		@Parameter(description = "검색 기록 ID", required = true, example = "1")
		@PathVariable Long searchId) {
		// searchId를 받아서 클릭 처리
		return searchService.clickAddress(searchId);
	}
}

