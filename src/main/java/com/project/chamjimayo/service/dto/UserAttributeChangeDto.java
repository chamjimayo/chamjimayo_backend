package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.UserAttributeChangeResponse;
import lombok.Getter;

@Getter
public class UserAttributeChangeDto {
  private final String attribute;

  private UserAttributeChangeDto(String attribute) {
    this.attribute = attribute;
  }

  public static UserAttributeChangeDto create(String attribute) {
    return new UserAttributeChangeDto(attribute);
  }

  public UserAttributeChangeResponse toResponse() {
    return UserAttributeChangeResponse.create(attribute);
  }
}
