package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.RestroomSummaryResponse;
import com.project.chamjimayo.repository.domain.entity.RestroomPhoto;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class RestroomSummaryDto {

  private final Long id;
  private final String restroomName;
  private final String roadAddress;
  private final Integer reviewCount;
  private final String operatingHour;
  private final Integer point;
  private final List<RestroomPhoto> photos;

  public RestroomSummaryDto(Long id, String restroomName, String roadAddress, Integer reviewCount,
      String operatingHour, Integer point, List<RestroomPhoto> photos) {
    this.id = id;
    this.restroomName = restroomName;
    this.roadAddress = roadAddress;
    this.reviewCount = reviewCount;
    this.operatingHour = operatingHour;
    this.point = point;
    this.photos = photos;
  }

  public RestroomSummaryResponse toResponse() {
    List<String> photoUrls = photos.stream()
        .map(RestroomPhoto::getPhotoUrl)
        .collect(Collectors.toList());

    return RestroomSummaryResponse.create(id, restroomName, roadAddress, reviewCount, operatingHour,
        point, photoUrls);
  }

  public static RestroomSummaryDto create(Long id, String restroomName, String roadAddress,
      Integer reviewCount, String operatingHour, Integer point, List<RestroomPhoto> photos) {
    return new RestroomSummaryDto(id, restroomName, roadAddress, reviewCount, operatingHour, point,
        photos);
  }

  public static RestroomSummaryDto empty() {
    return new RestroomSummaryDto(0L, "", "", 0, "",
        0, Collections.emptyList());
  }
}
