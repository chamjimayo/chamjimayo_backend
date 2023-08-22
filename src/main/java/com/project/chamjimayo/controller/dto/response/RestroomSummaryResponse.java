package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Schema(name = "RestroomSummaryDto.Response")
@Getter
public class RestroomSummaryResponse {

  private final Long id;
  private final String restroomName;
  private final String roadAddress;
  private final Integer reviewCount;
  private final String operatingHour;
  private final Integer point;
  private final List<String> restroomImageUrl;

  private RestroomSummaryResponse(Long id, String restroomName, String roadAddress,
      Integer reviewCount, String operatingHour, Integer point, List<String> restroomImageUrl) {
    this.id = id;
    this.restroomName = restroomName;
    this.roadAddress = roadAddress;
    this.reviewCount = reviewCount;
    this.operatingHour = operatingHour;
    this.point = point;
    this.restroomImageUrl = restroomImageUrl;
  }

  public static RestroomSummaryResponse create(Long id, String restroomName, String roadAddress,
      Integer reviewCount, String operatingHour, Integer point, List<String> restroomImageUrl) {
    return new RestroomSummaryResponse(id, restroomName, roadAddress, reviewCount, operatingHour,
        point, restroomImageUrl);
  }
}


