package com.project.chamjimayo.controller.dto.response;

import com.project.chamjimayo.repository.domain.entity.Restroom;
import com.project.chamjimayo.repository.domain.entity.RestroomPhoto;
import com.project.chamjimayo.service.dto.EquipmentNameNId;
import com.project.chamjimayo.service.dto.RestroomManagerNameNId;
import com.project.chamjimayo.service.dto.ReviewContentNId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class RestroomDetailResponse {

  private String restroomName;

  private double longitude;

  private double latitude;

  // 남여 공용 화장실인가?
  private Boolean unisex;

  // 도로명 주소
  private String address;

  // 개방 시간
  private String operatingHour;

  // 화장실 사진 url
  private List<String> restroomPhoto;

  // 비품이 있을 확률 -> 어떤 비품이 있는 확률인지...?
  private double equipmentExistenceProbability;

  // 공용(무료)인가 유료인가?
  private String publicOrPaid;

  // 이용가능한 상태인가?
  private Boolean accessibleToiletExistence;

  // 남자 대변기 수
  private Integer maleToiletCount;

  // 여자 대변기 수
  private Integer femaleToiletCount;

  // 남자 이용 가능 대변기 수
  private Integer availableMaleToiletCount;

  // 여자 이용 가능 대변기 수
  private Integer availableFemaleToiletCount;

  private List<EquipmentNameNId> equipments;

  private List<ReviewContentNId> reviews;

  private RestroomManagerNameNId restroomManager;

  private Float averageRating;

  private Integer price;

  public RestroomDetailResponse(Restroom restroom) {
    this.restroomName = restroom.getRestroomName();
    this.longitude = restroom.getLocationLongitude();
    this.latitude = restroom.getLocationLatitude();
    this.unisex = restroom.getUnisex();
    this.address = restroom.getAddress();
    this.operatingHour = restroom.getOperatingHour();
    this.restroomPhoto = restroom.getRestroomPhotos().stream()
        .map(RestroomPhoto::getPhotoUrl)
        .collect(Collectors.toList());
    this.equipmentExistenceProbability = restroom.getEquipmentExistenceProbability();
    this.publicOrPaid = restroom.getPublicOrPaid();
    this.accessibleToiletExistence = restroom.getAccessibleToiletExistence();
    this.maleToiletCount = restroom.getMaleToiletCount();
    this.femaleToiletCount = restroom.getFemaleToiletCount();
    this.availableMaleToiletCount = restroom.getAvailableMaleToiletCount();
    this.availableFemaleToiletCount = restroom.getAvailableFemaleToiletCount();
    this.equipments = restroom.getEquipments()
        .stream().map(equipment -> new EquipmentNameNId(equipment.getEquipmentName(),
            equipment.getEquipmentId()))
        .collect(Collectors.toList());
    this.reviews = restroom.getReviews()
        .stream()
        .map(review -> new ReviewContentNId(review.getReviewContent(), review.getReviewId()))
        .collect(Collectors.toList());
    if (restroom.getRestroomManager() == null) {
      this.restroomManager = null;
    } else {
      this.restroomManager = new RestroomManagerNameNId(
          restroom.getRestroomManager().getName(),
          restroom.getRestroomManager().getManagerId());
    }
    this.averageRating = restroom.getAverageRating();
    this.price = restroom.getPrice();
  }
}
