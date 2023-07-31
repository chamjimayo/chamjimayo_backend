package com.project.chamjimayo.service;

import com.jayway.jsonpath.JsonPath;
import com.project.chamjimayo.controller.dto.SearchResponseDto;
import com.project.chamjimayo.domain.entity.Search;
import com.project.chamjimayo.domain.entity.User;
import com.project.chamjimayo.exception.ApiNotFoundException;
import com.project.chamjimayo.exception.UserNotFoundException;
import com.project.chamjimayo.repository.SearchRepository;
import com.project.chamjimayo.repository.UserJpaRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.transaction.annotation.Transactional;
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
   * 검색어에 대한 주소 검색을 수행하고, 검색 결과를 반환
   */
  @Transactional
  public List<SearchResponseDto> searchAddress(String searchWord, Long userId) {
    // 검색 결과 가져올 개수와 검색어 설정
    int count = 7;

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

    // Json 파일이 제대로 도착했는지 확인
    String responseBody = responseEntity.getBody();
    if (responseBody == null || responseBody.isEmpty()) {
      throw new ApiNotFoundException("Api 응답이 올바르지 않습니다.");
    }

    // 사용자 정보 가져오기
    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다."));

    // API 응답에서 주소, 이름 리스트 추출
    List<String> fullAddressRoadList = extractFullAddressRoad(responseEntity.getBody());
    List<String> lotNumberAddressList = extractLotNumberAddress(responseEntity.getBody());
    List<String> nameList = extractName(responseEntity.getBody());
    List<Double> latitudeList = extractLatitude(responseEntity.getBody());
    List<Double> longitudeList = extractLongitude(responseEntity.getBody());

    List<SearchResponseDto> searchResponseDTOList = new ArrayList<>();

    // 검색 리시트를 각 주소마다 분리해서 Search 엔티티로 저장
    for (int i = 0; i < fullAddressRoadList.size(); i++) {
      String fullAddressRoad = fullAddressRoadList.get(i);
      String lotNumberAddress = lotNumberAddressList.get(i);
      String name = nameList.get(i);
      Double latitude = latitudeList.get(i);
      Double longitude = longitudeList.get(i);

      // 검색 결과 DTO 생성 및 추가
      SearchResponseDto responseDTO = SearchResponseDto.create(searchWord, fullAddressRoad,
          lotNumberAddress, name, latitude, longitude);
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
   * API 응답에서 위도를 추출하여 반환
   */
  private List<Double> extractLatitude(String responseBody) {
    List<Double> latitudeList = new ArrayList<>();

    // JSON 응답에서 frontLat와 noorLat 추출하여 평균 계산
    List<String> frontLatList = JsonPath.read(responseBody,
        "$.searchPoiInfo.pois.poi[*].frontLat");
    List<String> noorLatList = JsonPath.read(responseBody,
        "$.searchPoiInfo.pois.poi[*].noorLat");

    for (int i = 0; i < frontLatList.size(); i++) {
      double frontLat = Double.parseDouble(frontLatList.get(i));
      double noorLat = Double.parseDouble(noorLatList.get(i));
      double averageLat = (frontLat + noorLat) / 2.0;
      // 소수점 7번째까지
      averageLat = Math.round(averageLat * 10000000) / 10000000.0;
      latitudeList.add(averageLat);
    }
    return latitudeList;
  }

  /**
   * API 응답에서 경도를 추출하여 반환
   */
  private List<Double> extractLongitude(String responseBody) {
    List<Double> longitudeList = new ArrayList<>();

    // 평균 계산
    List<String> frontLonList = JsonPath.read(responseBody,
        "$.searchPoiInfo.pois.poi[*].frontLon");
    List<String> noorLonList = JsonPath.read(responseBody,
        "$.searchPoiInfo.pois.poi[*].noorLon");

    for (int i = 0; i < frontLonList.size(); i++) {
      double frontLon = Double.parseDouble(frontLonList.get(i));
      double noorL0n = Double.parseDouble(noorLonList.get(i));
      double averageLon = (frontLon + noorL0n) / 2.0;
      // 소수점 7번째까지
      averageLon = Math.round(averageLon * 10000000) / 10000000.0;
      longitudeList.add(averageLon);
    }
    return longitudeList;
  }

  /**
   * 도로명 주소를 클릭한 경우
   */
  @Transactional
  public void clickAddress(Long userId, SearchResponseDto searchResponseDto) {

    // userRepository 에서 userId를 통해 user 객체를 가져옴
    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다."));
    String searchWord = searchResponseDto.getSearchWord();
    String roadAddress = searchResponseDto.getRoadAddress();
    String lotNumberAddress = searchResponseDto.getLotNumberAddress();
    String name = searchResponseDto.getName();
    Double latitude = searchResponseDto.getLatitude();
    Double longitude = searchResponseDto.getLongitude();

    Search search = Search.create(user, searchWord, roadAddress, lotNumberAddress, name,
        latitude, longitude);

    // 이전에 클릭하지 않은 경우 (db에 저장되지 않은 경우)만 저장한다. - 가게 이름(고유)으로 검증
    Optional<Search> existingSearch = searchRepository.findByUserAndName(user, name);
    // 값이 존재하는 경우, 삭제하고 다시 저장 (최신화를 위해)
    if (existingSearch.isPresent()) {
      searchRepository.delete(existingSearch.get());
    }
    searchRepository.save(search);
  }

  /**
   * 최근 검색 기록 삭제
   */
  @Transactional
  public void deleteRecentSearchHistory(Search search) {
    searchRepository.deleteById(search.getSearchId());
  }

  /**
   * 검색 기록 모두 삭제
   */
  @Transactional
  public void deleteRecentSearchHistoryAll(User user) {
    searchRepository.deleteAllByUser(user);
  }

  /**
   * 해당 유저의 최근 검색 기록 리스트 반환
   */
  public List<SearchResponseDto> getUserSearchHistory(Long userId) {
    // userRepository 에서 userId를 통해 user 객체를 가져옴
    User user = userJpaRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("유저를 찾지 못했습니다."));

    // userId에 해당하는 모든 검색 기록 반환
    List<Search> searchList = searchRepository.findAllByUser(user);

    List<SearchResponseDto> searchResponseDTOList = new ArrayList<>();

    // 검색 결과 DTO 리스트 생성
    for (Search searchHistory : searchList) {
      String searchWord = searchHistory.getSearchWord();
      String roadAddress = searchHistory.getRoadAddress();
      String lotNumberAddress = searchHistory.getLotNumberAddress();
      String name = searchHistory.getName();
      double latitude = searchHistory.getLatitude();
      double longitude = searchHistory.getLongitude();

      SearchResponseDto searchResponse = SearchResponseDto.create(searchWord, roadAddress,
          lotNumberAddress, name, latitude, longitude);
      searchResponseDTOList.add(searchResponse);
    }
    Collections.reverse(searchResponseDTOList);
    return searchResponseDTOList;
  }
}

