package com.project.chamjimayo.service.dto;

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

  @Schema(name = "RestroomSummaryDto.Response")
  @Getter
  public static class Response {

    private final Long id;

    private final String restroomName;

    private final String roadAddress;

    private final Integer reviewCount;

    private final String operatingHour;

    public Response(Long id, String restroomName, String roadAddress, Integer reviewCount,
        String operatingHour) {
      this.id = id;
      this.restroomName = restroomName;
      this.roadAddress = roadAddress;
      this.reviewCount = reviewCount;
      this.operatingHour = operatingHour;
    }
  }

  public Response toResponse() {
    return new Response(id, restroomName, roadAddress, reviewCount, operatingHour);
  }

  public static RestroomSummaryDto empty() {
    return new RestroomSummaryDto(0L, "", "", 0, "");
  }
}
