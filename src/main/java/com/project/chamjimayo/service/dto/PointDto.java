package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.request.PointRequest;
import com.project.chamjimayo.controller.dto.response.PointResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PointDto {
  @Schema(type = "Integer", example = "2000")
  @NotNull(message = "포인트를 입력해주세요.")
  @Min(value = 0, message = "포인트의 최솟값은 0입니다.")
  private Integer point;

  private PointDto(Integer point) {
    this.point = point;
  }

  public static PointDto create(Integer point) {
    return new PointDto(point);
  }

  public PointResponse toResponse() {
    return PointResponse.create( point);
  }
}
