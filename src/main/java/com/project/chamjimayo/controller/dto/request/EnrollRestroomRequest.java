package com.project.chamjimayo.controller.dto.request;

import com.project.chamjimayo.service.dto.EnrollRestroomDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnrollRestroomRequest {

  private String restroomName;

  // 주소
  private String address;

  // 개방 시간
  private String operatingHour;

  // 화장실 사진 url
  private List<String> imageUrl;

  // 남자 대변기 수
  private Integer maleToiletCount;

  // 여자 대변기 수
  private Integer femaleToiletCount;

  //유로 무료
  private String publicOrPaid;

  private long restroomManagerId = 0;

  private Integer price;

  public EnrollRestroomDto toDto() {
    return EnrollRestroomDto.create(restroomName, address, operatingHour, imageUrl, maleToiletCount,
        femaleToiletCount, publicOrPaid, price);
  }
}
