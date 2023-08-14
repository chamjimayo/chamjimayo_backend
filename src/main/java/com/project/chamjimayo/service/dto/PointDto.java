package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.request.PointRequest;
import com.project.chamjimayo.controller.dto.response.PointResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PointDto {

  @Schema(type = "Long", example = "1")
  @NotNull(message = "유저 ID를 입력해주세요.")
  private Long userId;

  @Schema(type = "Integer", example = "2000")
  @NotNull(message = "포인트를 입력해주세요.")
  @Min(value = 0, message = "포인트의 최솟값은 0입니다.")
  private Integer point;

  private PointDto(Long userId, Integer point) {
    this.userId = userId;
    this.point = point;
  }

  public static PointDto create(PointRequest pointRequest) {
    return new PointDto(pointRequest.getUserId(), pointRequest.getPoint());
  }

  public static PointDto create(Long userId, Integer point) {
    return new PointDto(userId, point);
  }

  public PointResponse toResponse() {
    return PointResponse.create(userId, point);
  }
}
