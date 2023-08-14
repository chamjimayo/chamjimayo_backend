package com.project.chamjimayo.service.dto;

import lombok.Getter;

@Getter
public class RestroomNearByDto {

  double longitude;

  double latitude;

  String publicOrPaidOrEntire;

  double distance;

  String sortBy;


  public RestroomNearByDto(double longitude, double latitude, String publicOrPaidOrEntire,
      double distance, String sortBy) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.publicOrPaidOrEntire = publicOrPaidOrEntire;
    this.distance = distance;
    this.sortBy = sortBy;
  }
}
