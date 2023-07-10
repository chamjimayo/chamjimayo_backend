package com.project.chamjimayo.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.repository.RestroomRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestroomService {

    RestroomRepository restroomRepository;
    Environment env;

    @Autowired
    public RestroomService(RestroomRepository restroomRepository,Environment env) {
        this.restroomRepository = restroomRepository;
        this.env = env;
    }

    public ArrayList<Map> readJson() throws Exception {
        /* local에 있는 파일 사용
        Reader reader = new FileReader("/Users/kick_sim/Downloads/seoulRestroom.json");
        JSONParser parser = new JSONParser(reader);
        ArrayList<Map> restroomList = (ArrayList<Map>) parser.parse();
        return restroomList;
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
            e.printStackTrace();
        }
        return null;
    }

    public double[] getLongNLat(String address) {
        String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="
            + address; //네이버 cloud platform GeoCoding 사용
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
        Map responseMap = responseArrayList.get(0);
        double longAndLat[] = {Double.parseDouble((String) responseMap.get("x")),
            Double.parseDouble((String) responseMap.get("y"))};
        return longAndLat;
    }

    public void importRestroom() throws Exception {
        ArrayList<Map> restroomList = readJson();
        for (Map restroom_info : restroomList) {
            //Map restroom_info = restroomList.get(1000); // TEST
            Restroom restroom = new Restroom();
            restroom.setRestroomName((String) restroom_info.get("화장실명"));
            double[] longNLat = getLongNLat(
                (String) restroom_info.get("소재지주소")); // 소재지 주소를 통해 위도 경도 검색
            restroom.setLocationLatitude(longNLat[1]);
            restroom.setLocationLongitude(longNLat[0]);
            //restroom.setUnisex(); 차후 개발 남성용, 여성용 변기 수로 만들면 될듯?
            restroom.setRoadAddress(
                (String) restroom_info.get("소재지주소")); // 도로명 주소가 존재하지 않는 경우도 있다 그런경우 지번 주소 삽입
            restroom.setOpeningHour(
                (String) restroom_info.get("개방시간")); // 개방시간에 대한 데이터의 형태가 다양함(Casting 해야할 수도?)
            restroom.setClosingHour((String) restroom_info.get(
                "개방시간")); // closinghour을 삭제하고 위에 openinghour와 묶어서 운영시간으로 하는것이 좋아보임
            restroom.setRestroomPhoto("이미지 URL"); // 차후 개발
            restroom.setEquipmentExistenceProbability(0); // 차후 개발
            restroom.setPublicOrPaid("public");
            restroom.setAccessibleToiletExistence(true);//이용 가능 상태 default로 true
            restroom.setMaleToiletCount(Integer.parseInt((String) restroom_info.get("남성용-대변기수")));
            restroom.setFemaleToiletCount(Integer.parseInt((String) restroom_info.get("여성용-대변기수")));
            restroom.setAvailableMaleToiletCount(
                Integer.parseInt((String) restroom_info.get("남성용-대변기수"))); // default를 전체 대변기 수로 설정
            restroom.setAvailableFemaleToiletCount(
                Integer.parseInt((String) restroom_info.get("여성용-대변기수"))); // default를 전체 대변기 수로 설정
            //restroomRepository.save(restroom); // 데이터베이스에 화장실 정보 저장
            System.out.println(restroom); //테스트
        }
    }
}
