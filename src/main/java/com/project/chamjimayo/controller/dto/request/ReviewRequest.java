package com.project.chamjimayo.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequest {

  @Schema(type = "Long", example = "1")
  @NotNull(message = "화장실 ID를 입력해주세요.")
  private Long restroomId;

  @Schema(type = "string", example = "깔끔해요!")
  @NotBlank(message = "리뷰 내용을 입력해주세요.")
  private String reviewContent;

  @Schema(type = "Integer", example = "4")
  @NotNull(message = "평점을 입력해주세요.")
  @Min(value = 0, message = "평점은 0 ~ 5점으로 입력해주세요.")
  @Max(value = 5, message = "평점은 0 ~ 5점으로 입력해주세요.")
  private Integer rating;
}
