package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.SearchRequestDto;
import com.project.chamjimayo.controller.dto.SearchResponseDto;
import com.project.chamjimayo.repository.SearchRepository;
import com.project.chamjimayo.service.SearchService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class SearchController {

	private final SearchService searchService;
	private final SearchRepository searchRepository;

	@Autowired
	public SearchController(SearchService searchService, SearchRepository searchRepository) {
		this.searchService = searchService;
		this.searchRepository = searchRepository;
	}

	/**
	 * 검색어와 유저 id를 받아서 검색어에 대한 도로명 주소, 지번 주소, 가게 이름을 반환 searchAddress 의 count 변수로 조절 가능 예시:
	 * /address/search?searchWord={검색어}&userId={유저 id}
	 */
	@GetMapping("/search")
	public ResponseEntity<List<SearchResponseDto>> getAddress(
		@RequestParam("searchWord") String searchWord, @RequestParam("userId") Integer userId) {
		SearchRequestDto requestDTO = new SearchRequestDto(searchWord, userId);
		List<SearchResponseDto> SearchResponseDTOList = searchService.searchAddress(requestDTO);
		return ResponseEntity.ok(SearchResponseDTOList);
	}

	/**
	 * 유저 아이디를 받아서 해당 유저의 최근 검색 기록 (도로명 주소, 지번 주소, 이름) 예시: /address/search/recent/{유저 id}
	 */
	@GetMapping("/search/recent/{userId}")
	public ResponseEntity<SearchResponseDto> getRecentAddress(@PathVariable Integer userId) {
		// userId를 통해 최근 검색 기록을 가져옴
		SearchResponseDto responseDTO = searchService.getRecentRoadAddress(userId);

		// 검색 결과를 ResponseEntity.ok() 메서드를 사용하여 성공적인 응답으로 반환
		return ResponseEntity.ok(responseDTO);
	}

	/**
	 * 도로명 주소를 클릭한 경우 해당 도로명 주소의 상태를 변경 -> 해당 주소를 클릭 처리하면 최종적으로 검색한 것으로 처리 예시:
	 * /address/search/click/{searchId}
	 */
	@PostMapping("/search/click/{searchId}")
	public ResponseEntity<String> clickAddress(@PathVariable Integer searchId) {
		// searchId를 받아서 클릭 처리
		return searchService.clickAddress(searchId);
	}

}


