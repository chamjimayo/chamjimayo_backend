package com.project.chamjimayo.controller.dto.request;

import com.project.chamjimayo.service.dto.UserAttributeChangeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAttributeChangeRequest {
  @NotBlank
  @Schema(description ="변경할 사용자 속성")
  private String attribute;

  public UserAttributeChangeDto toDto() {
    return UserAttributeChangeDto.create(attribute);
  }
}
