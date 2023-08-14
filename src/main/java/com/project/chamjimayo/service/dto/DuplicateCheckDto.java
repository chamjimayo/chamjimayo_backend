package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.DuplicateCheckResponse;
import lombok.Getter;

@Getter
public class DuplicateCheckDto {

  private final boolean isDuplicate;

  private DuplicateCheckDto(boolean isDuplicate) {
    this.isDuplicate = isDuplicate;
  }

  public static DuplicateCheckDto create(boolean isDuplicate) {
    return new DuplicateCheckDto(isDuplicate);
  }
  public DuplicateCheckResponse toResponse() {
    return DuplicateCheckResponse.create(isDuplicate);
  }
}
