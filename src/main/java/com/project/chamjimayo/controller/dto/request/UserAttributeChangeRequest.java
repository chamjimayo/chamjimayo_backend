package com.project.chamjimayo.controller.dto.request;

import com.project.chamjimayo.service.dto.UserAttributeChangeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserAttributeChangeRequest {
  @NotBlank
  @Schema(description ="변경할 사용자 속성")
  private final String attribute;

  public UserAttributeChangeRequest(String attribute) {
    this.attribute = attribute;
  }

  public UserAttributeChangeDto toDto() {
    return UserAttributeChangeDto.create(attribute);
  }
}
