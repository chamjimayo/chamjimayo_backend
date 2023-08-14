package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.RestroomSummaryResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RestroomSummaryDto {

  private final Long id;

  private final String restroomName;

  private final String roadAddress;

  private final Integer reviewCount;

  private final String operatingHour;

  public RestroomSummaryDto(Long id, String restroomName, String roadAddress, Integer reviewCount,
      String operatingHour) {
    this.id = id;
    this.restroomName = restroomName;
    this.roadAddress = roadAddress;
    this.reviewCount = reviewCount;
    this.operatingHour = operatingHour;
  }

  public RestroomSummaryResponse toResponse() {
    return RestroomSummaryResponse.create(id, restroomName, roadAddress, reviewCount, operatingHour);
  }

  public static RestroomSummaryDto empty() {
    return new RestroomSummaryDto(0L, "", "", 0, "");
  }
}
