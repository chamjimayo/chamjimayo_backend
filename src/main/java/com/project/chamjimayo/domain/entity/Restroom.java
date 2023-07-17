package com.project.chamjimayo.domain.entity;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "restroom")
@Getter
@ToString(exclude = "restroomId")
@NoArgsConstructor
public class Restroom extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restroom_id")
  private Long restroomId;

  @Column(name = "restroom_name")
  private String restroomName;

  // 위도
  @Column(name = "location_latitude")
  private double locationLatitude;

  // 경도
  @Column(name = "location_longitude")
  private double locationLongitude;

  // 남여 공용 화장실인가?
  @Column(name = "unisex")
  @Pattern(regexp = "[01]")
  private Boolean unisex;

  // 도로명 주소
  @Column(name = "address")
  private String roadAddress;

  // 개방 시간
  @Column(name = "operating_hour")
  private String operatingHour;

  // 화장실 대표 사진 url
  @Column(name = "restroom_photo")
  private String restroomPhoto;

  // 비품이 있을 확률 -> 어떤 비품이 있는 확률인지...?
  @Column(name = "equipment_existence_probability")
  private double equipmentExistenceProbability;

  // 공용(무료)인가 유료인가?
  @Column(name = "public_or_paid")
  private String publicOrPaid;

  // 이용가능한 상태인가?
  @Column(name = "accessible_toilet_existence")
  @Pattern(regexp = "[01]")
  private Boolean accessibleToiletExistence;

  // 남자 대변기 수
  @Column(name = "male_toilet_count")
  private Integer maleToiletCount;

  // 여자 대변기 수
  @Column(name = "female_toilet_count")
  private Integer femaleToiletCount;

  // 남자 이용 가능 대변기 수
  @Column(name = "available_male_toilet_count")
  private Integer availableMaleToiletCount;

  // 여자 이용 가능 대변기 수
  @Column(name = "available_female_toilet_count")
  private Integer availableFemaleToiletCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restroom_manager_id")
  private RestroomManager restroomManager;

  @OneToMany(mappedBy = "restroom")
  private List<Equipment> equipments;

  @OneToMany(mappedBy = "restroom")
  private List<Review> reviews;

  @Builder
  public Restroom(String restroomName, double locationLatitude, double locationLongitude,
      String roadAddress, String operatingHour, String restroomPhoto,
      double equipmentExistenceProbability, String publicOrPaid,
      boolean accessibleToiletExistence, int maleToiletCount, int femaleToiletCount,
      int availableMaleToiletCount, int availableFemaleToiletCount) {
    this.restroomName = restroomName;
    this.locationLatitude = locationLatitude;
    this.locationLongitude = locationLongitude;
    this.roadAddress = roadAddress;
    this.operatingHour = operatingHour;
    this.restroomPhoto = restroomPhoto;
    this.equipmentExistenceProbability = equipmentExistenceProbability;
    this.publicOrPaid = publicOrPaid;
    this.accessibleToiletExistence = accessibleToiletExistence;
    this.maleToiletCount = maleToiletCount;
    this.femaleToiletCount = femaleToiletCount;
    this.availableMaleToiletCount = availableMaleToiletCount;
    this.availableFemaleToiletCount = availableFemaleToiletCount;
  }
}
