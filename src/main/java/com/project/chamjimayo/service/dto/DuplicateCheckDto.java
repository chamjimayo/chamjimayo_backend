package com.project.chamjimayo.service.dto;

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
}
