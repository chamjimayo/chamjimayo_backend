package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.RestroomResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnrollRestroomDto {

  private long restroomId;

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

  private EnrollRestroomDto(String restroomName, String address, String operatingHour,
      List<String> imageUrl, Integer maleToiletCount, Integer femaleToiletCount,String publicOrPaid, Integer price){
    this.restroomName = restroomName;
    this.address = address;
    this.operatingHour = operatingHour;
    this.imageUrl = imageUrl;
    this.maleToiletCount = maleToiletCount;
    this.femaleToiletCount = femaleToiletCount;
    this.publicOrPaid = publicOrPaid;
    this.price = price;
  }
  public static EnrollRestroomDto create(String restroomName, String address, String operatingHour,
      List<String> imageUrl, Integer maleToiletCount, Integer femaleToiletCount,String publicOrPaid,Integer price){
    return new EnrollRestroomDto(restroomName,address,operatingHour,imageUrl,maleToiletCount,femaleToiletCount,publicOrPaid,price);
  }
  public void setRestroomId(long restroomId) {
    this.restroomId = restroomId;
  }
  public RestroomResponse toResponse(){return new RestroomResponse(restroomId,restroomName);}
}
