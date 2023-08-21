package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserAttributeChangeResponse {
  @Schema(description = "변경된 필드 값")
  private final String attribute;

  private UserAttributeChangeResponse(String attribute) {
    this.attribute = attribute;
  }

  public static UserAttributeChangeResponse create(String attribute) {
    return new UserAttributeChangeResponse(attribute);
  }
}
