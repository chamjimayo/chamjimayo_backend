package com.project.chamjimayo.service;

import com.jayway.jsonpath.JsonPath;
import com.project.chamjimayo.domain.entity.Search;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.dto.SearchRequestDTO;
import com.project.chamjimayo.dto.SearchResponseDTO;
import com.project.chamjimayo.repository.SearchRepository;
import com.project.chamjimayo.repository.UserRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SearchService {

	private final SearchRepository searchRepository;
	private final UserRepository userRepository;

	// application.yml에 저장된 t-map AppKey
	@Value("${tmap.appKey}")
	private String tmapApikey;

	@Autowired
	public SearchService(SearchRepository searchRepository, UserRepository userRepository) {
		this.searchRepository = searchRepository;
		this.userRepository = userRepository;
	}

	/**
	 * 검색어에 대한 도로명 주소 검색을 수행하고, 검색 결과를 반환
	 */
	public SearchResponseDTO searchAddress(SearchRequestDTO requestDTO) {
		// 검색 결과 가져올 개수와 검색어 설정
		int count = 5;
		String searchWord = requestDTO.getSearchWord();
		Integer userId = requestDTO.getUserId();

		// Tmap API 호출을 위한 URI 설정
		URI uri = UriComponentsBuilder
			.fromUriString("https://apis.openapi.sk.com")
			.path("/tmap/pois")
			.queryParam("version", 1)
			.queryParam("searchKeyword", searchWord)
			.queryParam("count", count)
			.build()
			.encode()
			.toUri();

		// RestTemplate을 사용하여 API 호출
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("appKey", tmapApikey);
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

		ResponseEntity<String> responseEntity;
		try {
			responseEntity = restTemplate.exchange(requestEntity, String.class);
		} catch (HttpClientErrorException e) {
			throw new IllegalArgumentException("T-Map API 에서 주소 정보를 찾지 못했습니다.");
		}

		// 사용자 정보 가져오기
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 userId 입니다."));

		// API 응답에서 주소 리스트 추출
		List<String> fullAddressRoadList = extractFullAddressRoad(responseEntity.getBody());

		// 각 주소를 Search 엔티티로 저장
		for (String fullAddressRoad : fullAddressRoadList) {
			Search search = new Search(user, searchWord, fullAddressRoad);
			searchRepository.save(search);
		}

		// 검색 결과 DTO 생성 및 반환
		SearchResponseDTO responseDTO = new SearchResponseDTO(
			String.join("\n", fullAddressRoadList));

		return responseDTO;
	}

	/**
	 * API 응답에서 도로명 주소를 추출하여 리스트로 반환
	 */
	private List<String> extractFullAddressRoad(String responseBody) {
		/**
		 * JSON 문자열에서 fullAddressRoad 추출하여 리스트로 반환
		 * searchPoiInfo의 pois의 poi의 모든 요소 중
		 * newAddressList의 newAddress의 모든 요소 중
		 * fullyAddressRoad 값을 반환한다.
		 */
		return JsonPath.read(responseBody,
			"$.searchPoiInfo.pois.poi[*].newAddressList.newAddress[*].fullAddressRoad");
	}

	/**
	 * 유저 아이디에 해당하는 사용자의 최근 검색어를 가져와서 검색 결과 DTO로 반환
	 */
	public SearchResponseDTO getRecentSearchWord(Integer userId) {
		// userRepository에서 userId를 통해 user 객체를 가져옴
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 userId 입니다."));

		// 가장 최근에 검색된 상태가 1인 도로명 주소 가져오기
		Optional<Search> searchOptional = searchRepository.findTopByUserAndClickOrderBySearchIdDesc(user, 1);
		String recentSearchWord = searchOptional.map(Search::getRoadAddress).orElse("");

		// 검색 결과 DTO 생성 및 반환
		SearchResponseDTO responseDTO = new SearchResponseDTO(recentSearchWord);
		return responseDTO;
	}
}

