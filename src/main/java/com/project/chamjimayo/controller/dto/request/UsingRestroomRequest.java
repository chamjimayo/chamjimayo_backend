package com.project.chamjimayo.controller.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UsingRestroomRequest {

  @NotBlank
  private long restroomId;
}
