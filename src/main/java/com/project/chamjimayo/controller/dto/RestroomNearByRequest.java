package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public class RestroomNearByRequest {

  double longitude;

  double latitude;

  String publicOrPaidOrEntire;

  double distance;

  String sortBy;

  public RestroomNearByRequest(double longitude, double latitude, String publicOrPaidOrEntire,
      double distance, String sortBy) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.publicOrPaidOrEntire = publicOrPaidOrEntire;
    this.distance = distance;
    this.sortBy = sortBy;
  }
}
