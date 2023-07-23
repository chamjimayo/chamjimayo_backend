package com.project.chamjimayo.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.controller.dto.EnrollRestroomRequest;
import com.project.chamjimayo.controller.dto.NearByResponse;
import com.project.chamjimayo.controller.dto.RestroomDetailResponse;
import com.project.chamjimayo.controller.dto.RestroomNearByRequest;
import com.project.chamjimayo.controller.dto.RestroomResponse;
import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.exception.AddressNotFoundException;
import com.project.chamjimayo.exception.FileNotFoundException;
import com.project.chamjimayo.exception.IoException;
import com.project.chamjimayo.exception.RestroomNameDuplicateException;
import com.project.chamjimayo.exception.RestroomNotFoundException;
import com.project.chamjimayo.repository.RestroomJpaRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestroomService {

	private final RestroomJpaRepository restroomJpaRepository;
	private final Environment env;

	/*공공화장실 데이터가 담긴 json 파일 읽어오기*/
	public ArrayList<Map> readJson() {
        /*//local에 있는 파일 사용
        Reader reader = null;
        try {
            reader = new FileReader("/Users/kick_sim/Downloads/seoulRestroom.json");
            JSONParser parser = new JSONParser(reader);
            ArrayList<Map> restroomList = (ArrayList<Map>) parser.parse();
            return restroomList;
        } catch (FileNotFoundException | ParseException e) {
            throw new BaseException(ErrorCode.FILE_NOT_FOUND);
        }
        */

		/* 구글 드라이브에 공유된 파일 사용*/
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Resource> response = restTemplate.exchange(
			"https://drive.google.com/uc?id=1t0hgQV9Ud4MDYYFj2z11EdwxyPd1265W&export=download",
			HttpMethod.GET,
			null,
			Resource.class
		);
		Resource resource = response.getBody(); // Resource객체 추출, Resource는 추상화된 리소스를 다루는 인터페이스임
		if (resource.exists() == false) {
			throw new FileNotFoundException("파일을 찾을 수 없습니다");
		}

		/*(Resource) 객체에서 InputStream을 얻어옴. InputStream은 파일의 내용을 바이트 스트림 형태로 읽어오는데 사용*/
		try (InputStream inputStream = resource.getInputStream()) {
			/*JSON 데이터를 파싱하기 위한 ObjectMapper 객체를 생성*/
			ObjectMapper objectMapper = new ObjectMapper();
			/* ObjectMapper를 사용해 ArrayList<Map> 형태의 자바 객체로 변환 */
			ArrayList<Map> dataObject = objectMapper.readValue(inputStream,
				new TypeReference<ArrayList<Map>>() {
				});
			return dataObject;
		} catch (IOException e) {
			throw new IoException("입출력 오류 발생");
		}
	}

	/*특정 주소의 좌표를 검색*/
	public double[] getLongNLat(String address) {
		String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="
			+ address; //네이버 cloud platform GeoCoding 사용
		ArrayList<Map> responseArrayList = geocoding(apiUrl);
		Map responseMap = responseArrayList.get(0);
		if (responseMap.isEmpty()) {
			throw new AddressNotFoundException("잘못된 주소입니다");
		}
		double longAndLat[] = {Double.parseDouble((String) responseMap.get("x")),
			Double.parseDouble((String) responseMap.get("y"))};
		return longAndLat;
	}

	/*남여공용 화장실인지 확인*/
	public boolean checkSex(Map restroom) {
		if (Integer.parseInt((String) restroom.get("남성용-대변기수")) > 0
			&& Integer.parseInt((String) restroom.get("여성용-대변기수")) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*남여공용 화장실인지 확인*/
	public boolean checkSex(int maleToiletCount, int femaleToiletCount) {
		if (maleToiletCount > 0 && femaleToiletCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*apiUrl을 받아서 geocoding 결과를 반환해주는 func*/
	public ArrayList<Map> geocoding(String apiUrl) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		String client_Id = env.getProperty("naver.client-id");
		String client_Secret = env.getProperty("naver.client-secret");
		httpHeaders.add("X-NCP-APIGW-API-KEY-ID", client_Id);
		httpHeaders.add("X-NCP-APIGW-API-KEY", client_Secret);
		ResponseEntity<Map> response = restTemplate.exchange(
			apiUrl,
			HttpMethod.GET,
			new HttpEntity(httpHeaders),
			Map.class
		);
		ArrayList<Map> responseArrayList = (ArrayList<Map>) response.getBody().get("addresses");
		if (responseArrayList.isEmpty()) {
			throw new AddressNotFoundException("유효하지 않은 주소입니다 다시 확인해주세요!");
		}
		return responseArrayList;
	}

	/* 공공 화장실 데이터 저장 */
	public List<RestroomResponse> importRestroom() {
		ArrayList<Map> restroomList = null;
		restroomList = readJson();
		List<RestroomResponse> response = new ArrayList<>();
		for (Map restroom_info : restroomList) {

			//Map restroom_info = restroomList.get(51); // test
			double[] longNLat = getLongNLat(
				(String) restroom_info.get("소재지주소")); // 소재지 주소를 통해 위도 경도 검색
			Restroom restroom = Restroom.builder()
				.restroomName((String) restroom_info.get("화장실명"))
				.locationLatitude(longNLat[1])
				.locationLongitude(longNLat[0])
				.unisex(checkSex(restroom_info)) // 남여공용이면 true 아니면 false
				//restroomManager 차후개발
				.address((String) restroom_info.get("소재지주소"))
				.operatingHour((String) restroom_info.get("개방시간"))
				.restroomPhoto("이미지 URL") // 차후개발
				.equipmentExistenceProbability(0)//차후개발
				.publicOrPaid("public")
				.accessibleToiletExistence(true) // 이용 가능 상태 default로 true
				.maleToiletCount(Integer.parseInt((String) restroom_info.get("남성용-대변기수")))
				.femaleToiletCount(Integer.parseInt((String) restroom_info.get("여성용-대변기수")))
				.availableMaleToiletCount(Integer.parseInt(
					(String) restroom_info.get("남성용-대변기수")))// default를 전체 대변기 수로 설정)
				.availableFemaleToiletCount(Integer.parseInt(
					(String) restroom_info.get("여성용-대변기수"))) // default를 전체 대변기 수로 설정
				.build();
			RestroomResponse restroomResponse = new RestroomResponse(
				restroomJpaRepository.save(restroom).getRestroomId(),
				restroom.getRestroomName()); // 데이터베이스에 화장실 정보 저장
			response.add(restroomResponse);
		}
		return response;
	}

	/* 유료 화장실 등록 */
	public RestroomResponse enrollRestroom(EnrollRestroomRequest enrollRestroomRequest) {
		if (restroomJpaRepository.existsRestroomByRestroomName(
			enrollRestroomRequest.getRestroomName())) {
			throw new RestroomNameDuplicateException("중복되는 화장실 명입니다.");
		}
		double[] longNLat = getLongNLat(enrollRestroomRequest.getAddress());
		Restroom restroom = Restroom.builder()
			.restroomName(enrollRestroomRequest.getRestroomName())
			.locationLatitude(longNLat[1])
			.locationLongitude(longNLat[0])
			.unisex(checkSex(enrollRestroomRequest.getMaleToiletCount(),
				enrollRestroomRequest.getFemaleToiletCount())) // 남여공용이면 true 아니면 false
//            .restroomManager(restroomManagerRepository.getReferenceById(
//                enrollRestroomRequest.getRestroomManagerId())) //restroomManager 차후개발
			.address(enrollRestroomRequest.getAddress())
			.operatingHour(enrollRestroomRequest.getOperatingHour())
			.restroomPhoto(enrollRestroomRequest.getRestroomPhoto())
			.equipmentExistenceProbability(0)
			.publicOrPaid(enrollRestroomRequest.getPublicOrPaid())
			.accessibleToiletExistence(true)
			.maleToiletCount(enrollRestroomRequest.getMaleToiletCount())
			.femaleToiletCount(enrollRestroomRequest.getFemaleToiletCount())
			.availableFemaleToiletCount(enrollRestroomRequest.getFemaleToiletCount())
			.availableMaleToiletCount(enrollRestroomRequest.getMaleToiletCount())
			.build();
		RestroomResponse response = new RestroomResponse(
			restroomJpaRepository.save(restroom).getRestroomId(), restroom.getRestroomName());
		return response;
	}

	/* 주어진 distance안에 있는 화장실인지 아닌지 판별 */
	public boolean isNearBy(RestroomNearByRequest request, String address) {
		String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="
			+ address + "&coordinate=" + request.getLongitude() + ","
			+ request.getLatitude(); //네이버 cloud platform GeoCoding 사용
		ArrayList<Map> responseArrayList = geocoding(apiUrl);
		Map responseMap = responseArrayList.get(0);
		if (request.getDistance() >= (double) responseMap.get("distance")) {
			return true;
		} else {
			return false;
		}
	}

	public double calculateDistance(RestroomNearByRequest req, Restroom restroom) {
		final double EARTH_RADIUS_KM = 6371.0;
		double lat1 = req.getLatitude();
		double lon1 = req.getLongitude();
		double lat2 = restroom.getLocationLatitude();
		double lon2 = restroom.getLocationLongitude();
		// 각도를 라디안으로 변환
		double lat1Rad = Math.toRadians(lat1);
		double lon1Rad = Math.toRadians(lon1);
		double lat2Rad = Math.toRadians(lat2);
		double lon2Rad = Math.toRadians(lon2);

		// 위도 및 경도의 차이 계산
		double deltaLat = lat2Rad - lat1Rad;
		double deltaLon = lon2Rad - lon1Rad;

		// Haversine 공식 계산
		double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
			Math.cos(lat1Rad) * Math.cos(lat2Rad) *
				Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = EARTH_RADIUS_KM * c;

		return distance * 1000;
	}

	/* 주어진 좌표 주변 유/무료 화장실 검색 후 리스트 반환*/
	@Transactional(readOnly = true)
	public List<NearByResponse> nearBy(RestroomNearByRequest request) {
		Optional<List<Restroom>> restroomList;
		if (request.getPublicOrPaidOrEntire().equals("entire")) {
			restroomList = Optional.of(restroomJpaRepository.findAll());
		} else {
			restroomList = restroomJpaRepository.findPublicOrPaid(
				request.getPublicOrPaidOrEntire());
		}
		List<NearByResponse> nearByList = new ArrayList<>();
		if (restroomList.isEmpty()) {
			throw new RestroomNotFoundException("근처 화장실을 찾을 수 없습니다");
		}
		for (Restroom restroom : restroomList.get()) {
			double distance = calculateDistance(request, restroom);
			if (distance <= request.getDistance()) {
				restroom.getReviews().size(); // lazy initialize 문제 때문에 추가
				NearByResponse responseDto = new NearByResponse();
				responseDto = responseDto.makeDto(restroom, distance);
				nearByList.add(responseDto);
			}
		}
		List<NearByResponse> sortedList = nearByList.stream()
			.sorted(Comparator.comparingDouble(NearByResponse::getDistance))
			// .sorted(Comparator.comparingDouble(Response::getDistance).reversed())
			.collect(Collectors.toList());
		return sortedList;
	}

	/* 화장실 Id를 통해 화장실 세부 정보 검색 */
	@Transactional(readOnly = true)
	public RestroomDetailResponse restroomDetail(long restroomId) {
		Optional<Restroom> restroomOp = Optional.ofNullable(
			restroomJpaRepository.findRestroomByRestroomId(restroomId)
				.orElseThrow(() -> new RestroomNotFoundException("화장실을 찾을 수 없습니다")));
		Restroom restrooms = restroomOp.get();
		restroomOp.get().getReviews().size(); // lazy initialize 문제 때문에 추가
		restroomOp.get().getEquipments().size(); // lazy initialize 문제 때문에 추가
		RestroomDetailResponse responseDto = new RestroomDetailResponse();
		responseDto = responseDto.makeDto(restrooms);
		return responseDto;
	}
}
