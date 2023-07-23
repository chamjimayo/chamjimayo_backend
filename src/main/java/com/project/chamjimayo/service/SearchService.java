package com.project.chamjimayo.service;

import com.jayway.jsonpath.JsonPath;
import com.project.chamjimayo.controller.dto.SearchRequestDto;
import com.project.chamjimayo.controller.dto.SearchResponseDto;
import com.project.chamjimayo.domain.entity.Search;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.ApiNotFoundException;
import com.project.chamjimayo.exception.JsonFileNotFoundException;
import com.project.chamjimayo.exception.SearchHistoryNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.SearchRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SearchService {

	private final UserJpaRepository userJpaRepository;
	private final SearchRepository searchRepository;

	// application-local.yml에 저장된 t-map AppKey
	@Value("${tmap.appKey}")
	private String tmapApikey;


	/**
	 * 검색어에 대한 도로명 주소 검색을 수행하고, 검색 결과를 반환
	 */
	public List<SearchResponseDto> searchAddress(SearchRequestDto requestDTO) {
		// 검색 결과 가져올 개수와 검색어 설정
		int count = 5;
		String searchWord = requestDTO.getSearchWord();
		Long userId = requestDTO.getUserId();

		// Tmap API 호출을 위한 URI 설정
		URI uri = UriComponentsBuilder.fromUriString("https://apis.openapi.sk.com")
			.path("/tmap/pois").queryParam("version", 1).queryParam("searchKeyword", searchWord)
			.queryParam("count", count).build().encode().toUri();

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
			throw new ApiNotFoundException("Api 응답이 올바르지 않습니다.");
		}

		// 사용자 정보 가져오기
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다."));

		// Json 파일이 제대로 도착했는지 확인
		String responseBody = responseEntity.getBody();
		if (responseBody == null || responseBody.isEmpty()) {
			throw new JsonFileNotFoundException("Json 파일이 올바르지 않습니다.");
		}

		// API 응답에서 주소, 이름 리스트 추출
		List<String> fullAddressRoadList = extractFullAddressRoad(responseEntity.getBody());
		List<String> lotNumberAddressList = extractLotNumberAddress(responseEntity.getBody());
		List<String> nameList = extractName(responseEntity.getBody());

		List<SearchResponseDto> searchResponseDTOList = new ArrayList<>();

		// 검색 리시트를 각 주소마다 분리해서 Search 엔티티로 저장
		for (int i = 0; i < fullAddressRoadList.size(); i++) {
			String fullAddressRoad = fullAddressRoadList.get(i);
			String lotNumberAddress = lotNumberAddressList.get(i);
			String name = nameList.get(i);
			Search search = new Search(user, searchWord, fullAddressRoad, lotNumberAddress, name);
			searchRepository.save(search);

			// 검색 결과 DTO 생성 및 추가
			SearchResponseDto responseDTO = new SearchResponseDto(fullAddressRoad, lotNumberAddress,
				name);
			searchResponseDTOList.add(responseDTO);
		}
		return searchResponseDTOList;
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
	 * API 응답에서 지번 주소를 추출하여 리스트로 반환
	 */
	private List<String> extractLotNumberAddress(String responseBody) {
		List<String> upperAddrNameList = JsonPath.read(responseBody,
			"$.searchPoiInfo.pois.poi[*].upperAddrName");
		List<String> middleAddrNameList = JsonPath.read(responseBody,
			"$.searchPoiInfo.pois.poi[*].middleAddrName");
		List<String> lowerAddrNameList = JsonPath.read(responseBody,
			"$.searchPoiInfo.pois.poi[*].lowerAddrName");
		List<String> firstNoList = JsonPath.read(responseBody,
			"$.searchPoiInfo.pois.poi[*].firstNo");
		List<String> secondNoList = JsonPath.read(responseBody,
			"$.searchPoiInfo.pois.poi[*].secondNo");

		// 위에서 추출한 각 리스트들을 하나씩 받아서 모두 더해서 지번 주소 만들기
		List<String> lotNumberAddressList = new ArrayList<>();
		for (int i = 0; i < upperAddrNameList.size(); i++) {
			String upperAddrName = upperAddrNameList.get(i);
			String middleAddrName = middleAddrNameList.get(i);
			String lowerAddrName = lowerAddrNameList.get(i);
			String firstNo = firstNoList.get(i);
			String secondNo = secondNoList.get(i);

			StringBuilder lotNumberAddress = new StringBuilder();
			lotNumberAddress.append(upperAddrName).append(" ").append(middleAddrName).append(" ")
				.append(lowerAddrName).append(" ").append(firstNo);

			// secondNo가 없거나 0이면 없이 지번 주소 완성
			if ((secondNo != null) && (!secondNo.isEmpty()) && (!secondNo.equals("0"))) {
				lotNumberAddress.append(" ").append(secondNo);
			}

			lotNumberAddressList.add(lotNumberAddress.toString());
		}
		return lotNumberAddressList;
	}

	/**
	 * API 응답에서 가게 이름을 추출하여 리스트로 반환
	 */
	private List<String> extractName(String responseBody) {
		// searchPoiInfo의 pois의 poi의 모든 요소 중 name 값 반환
		return JsonPath.read(responseBody, "$.searchPoiInfo.pois.poi[*].name");
	}

	/**
	 * 유저 아이디에 해당하는 사용자의 최근 도로명 주소 가져와서 검색 결과 DTO로 반환
	 */
	public SearchResponseDto getRecentRoadAddress(Long userId) {
		// userRepository 에서 userId를 통해 user 객체를 가져옴
		User user = userJpaRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다."));

		// user 객체를 통해서 가장 최근에 검색된 상태가 1인 search 객체 받아오기
		Optional<Search> searchOptional = searchRepository.findTopByUserAndClickOrderBySearchIdDesc(
			user, 1);

		// 검색 결과 DTO 생성
		SearchResponseDto responseDTO;
		if (searchOptional.isPresent()) {
			Search search = searchOptional.get();
			responseDTO = new SearchResponseDto(search.getRoadAddress(),
				search.getLotNumberAddress(), search.getName());
		} else {
			// 빈 문자열 생성
			responseDTO = new SearchResponseDto("", "", "");
		}
		return responseDTO;
	}


	/**
	 * 도로명 주소를 클릭한 경우 해당 도로명 주소의 상태를 변경 -> 해당 주소를 클릭 처리하면 최종적으로 검색한 것으로 처리
	 */
	public ResponseEntity<String> clickAddress(Long searchId) {
		// searchId로 search 를 받아옴
		Search search = searchRepository.findById(searchId)
			.orElseThrow(() -> new SearchHistoryNotFoundException("검색 기록을 찾을 수 없습니다."));

		search.changeClick(1); // 도로명 주소의 상태를 클릭된 상태로 변경
		searchRepository.save(search);

		return ResponseEntity.ok("정상적으로 클릭이 되었습니다.");
	}
}

