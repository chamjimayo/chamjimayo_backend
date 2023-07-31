package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public class RestroomNearByRequest {

  double longitude;

  double latitude;

  String publicOrPaidOrEntire;

  double distance;

  public RestroomNearByRequest(double longitude, double latitude, String publicOrPaidOrEntire,
      double distance) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.publicOrPaidOrEntire = publicOrPaidOrEntire;
    this.distance = distance;
  }
}
