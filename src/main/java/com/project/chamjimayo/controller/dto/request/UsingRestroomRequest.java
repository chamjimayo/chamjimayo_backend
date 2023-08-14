package com.project.chamjimayo.controller.dto.request;

import com.project.chamjimayo.service.dto.EnrollRestroomDto;
import com.project.chamjimayo.service.dto.UsingRestroomDto;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UsingRestroomRequest {

  @NotBlank
  private long restroomId;
  public UsingRestroomDto toDto(long userId) {
    return UsingRestroomDto.create(userId,restroomId);
  }
}
