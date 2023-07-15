package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.Equipment;
import com.project.chamjimayo.domain.entity.Restroom;
import com.project.chamjimayo.domain.entity.RestroomManager;
import com.project.chamjimayo.domain.entity.Review;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

@Getter
public class RestroomNearByResponse {
    private String restroomName;
    private double longitude;
    private double latitude;
    // 남여 공용 화장실인가?
    private Boolean unisex;
    // 도로명 주소
    private String address;
    // 개방 시간
    private String operatingHour;
    // 화장실 대표 사진 url
    private String restroomPhoto;
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

    private List<Equipment> equipments;

    private List<Review> reviews;

    private RestroomManager restroomManager;

    public RestroomNearByResponse makeDto(Restroom restroom){
        this.restroomName = restroom.getRestroomName();
        this.longitude = restroom.getLocationLongitude();
        this.latitude = restroom.getLocationLatitude();
        this.unisex = restroom.getUnisex();
        this.address = restroom.getAddress();
        this.operatingHour = restroom.getOperatingHour();
        this.restroomPhoto = restroom.getRestroomPhoto();
        this.equipmentExistenceProbability = restroom.getEquipmentExistenceProbability();
        this.publicOrPaid = restroom.getPublicOrPaid();
        this.accessibleToiletExistence = restroom.getAccessibleToiletExistence();
        this.maleToiletCount = restroom.getMaleToiletCount();
        this.femaleToiletCount = restroom.getFemaleToiletCount();
        this.availableMaleToiletCount = restroom.getAvailableMaleToiletCount();
        this.availableFemaleToiletCount = restroom.getAvailableFemaleToiletCount();
        this.equipments = restroom.getEquipments();
        this.reviews = restroom.getReviews();
        this.restroomManager = restroom.getRestroomManager();
        return this;
    }
}
