package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "DuplicateCheckDto.Response")
@Getter
public class DuplicateCheckResponse {

  private final boolean isDuplicate;

  private DuplicateCheckResponse(boolean isDuplicate) {
    this.isDuplicate = isDuplicate;
  }

  public static DuplicateCheckResponse create(boolean isDuplicate) {
    return new DuplicateCheckResponse(isDuplicate);
  }
}