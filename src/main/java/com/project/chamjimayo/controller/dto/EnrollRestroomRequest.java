package com.project.chamjimayo.controller.dto;

import com.project.chamjimayo.domain.entity.Equipment;
import com.project.chamjimayo.domain.entity.RestroomManager;
import com.project.chamjimayo.domain.entity.Review;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
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
    // 화장실 대표 사진 url
    private String restroomPhoto;
    // 남자 대변기 수
    private Integer maleToiletCount;
    // 여자 대변기 수
    private Integer femaleToiletCount;
    //유로 무료
    private String publicOrPaid;

}
