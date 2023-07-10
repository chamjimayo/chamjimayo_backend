package com.project.chamjimayo.controller;

import com.project.chamjimayo.domain.entity.Search;
import com.project.chamjimayo.dto.SearchRequestDTO;
import com.project.chamjimayo.dto.SearchResponseDTO;
import com.project.chamjimayo.repository.SearchRepository;
import com.project.chamjimayo.repository.UserRepository;
import com.project.chamjimayo.service.SearchService;
import java.util.Optional;
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
	private final UserRepository userRepository;

	@Autowired
	public SearchController(SearchService searchService, SearchRepository searchRepository, UserRepository userRepository) {
		this.searchService = searchService;
		this.searchRepository = searchRepository;
		this.userRepository = userRepository;
	}

	/**
	 * 검색어와 유저 id를 받아서 검색어에 대한 도로명 주소를 반환
	 * count 변수로 조절 가능
	 * 예시: /address/search?searchWord={검색어}&userId={유저 id}
	 */
	@GetMapping("/search")
	public ResponseEntity<SearchResponseDTO> getAddress(
		@RequestParam("searchWord") String searchWord, @RequestParam("userId") Integer userId) {
		SearchRequestDTO requestDTO = new SearchRequestDTO(searchWord, userId);
		SearchResponseDTO responseDTO = searchService.searchAddress(requestDTO);
		return ResponseEntity.ok(responseDTO);
	}

	/**
	 * 유저 아이디를 받아서 해당 유저의 최근 도로명 주소를 반환
	 * 예시: /address/search/recent/{유저 id}
	 */
	@GetMapping("/search/recent/{userId}")
	public ResponseEntity<String> getRecentSearchWord(@PathVariable Integer userId) {
		// userId를 통해 최근 검색어를 가져오는 온다
		SearchResponseDTO responseDTO = searchService.getRecentSearchWord(userId);

		// 검색 결과에서 도로명 주소를 추출하여 recentSearchWord 변수에 할당
		String recentSearchWord = responseDTO.getRoadAddress();

		// 검색 결과를 ResponseEntity.ok() 메서드를 사용하여 성공적인 응답으로 반환
		return ResponseEntity.ok(recentSearchWord);
	}

	/**
	 * 도로명 주소를 클릭한 경우 해당 도로명 주소의 상태를 변경 -> 해당 주소를 클릭 처리하면 최종적으로 검색한 것으로 처리
	 * 예시: /address/search/click/{searchId}
	 */
	@GetMapping("/search/click/{searchId}")
	public ResponseEntity<String> clickAddress(@PathVariable Integer searchId) {
		// searchId로 search 를 받아옴
		Optional<Search> searchOptional = searchRepository.findById(searchId);
		if (searchOptional.isPresent()) {
			Search search = searchOptional.get();
			search.setClick(1); // 도로명 주소의 상태를 클릭된 상태로 변경
			searchRepository.save(search);
			return ResponseEntity.ok("정상적으로 클릭이 되었습니다.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}


