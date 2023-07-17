package com.project.chamjimayo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RestroomNearByRequest {
    double longitude;
    double latitude;
    String publicOrPaid;
    double distance;

    public RestroomNearByRequest(double longitude,double latitude,String publicOrPaid,double distance){
        this.longitude = longitude;
        this.latitude = latitude;
        this.publicOrPaid = publicOrPaid;
        this.distance = distance;
    }
    public RestroomNearByRequest(double longitude,double latitude,String publicOrPaid){
        this.longitude = longitude;
        this.latitude = latitude;
        this.publicOrPaid = publicOrPaid;
        this.distance = 1000; // 미터 단위
    }
}
