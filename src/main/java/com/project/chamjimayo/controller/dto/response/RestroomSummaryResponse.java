package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "RestroomSummaryDto.Response")
@Getter
public class RestroomSummaryResponse {

  private final Long id;
  private final String restroomName;
  private final String roadAddress;
  private final Integer reviewCount;
  private final String operatingHour;

  private RestroomSummaryResponse(Long id, String restroomName, String roadAddress,
      Integer reviewCount, String operatingHour) {
    this.id = id;
    this.restroomName = restroomName;
    this.roadAddress = roadAddress;
    this.reviewCount = reviewCount;
    this.operatingHour = operatingHour;
  }

  public static RestroomSummaryResponse create(Long id, String restroomName, String roadAddress,
      Integer reviewCount, String operatingHour) {
    return new RestroomSummaryResponse(id, restroomName, roadAddress, reviewCount, operatingHour);
  }
}


