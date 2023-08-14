package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.EndOfUsingRestroomResponse;
import com.project.chamjimayo.controller.dto.response.UsingRestroomResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class EndOfUsingRestroomDto {

  long userId;
  long restroomId;

  public EndOfUsingRestroomDto(long userId) {
    this.userId = userId;
  }

  public static EndOfUsingRestroomDto create(long userId) {
    return new EndOfUsingRestroomDto(userId);
  }

  public void setRestroomId(long restroomId) {
    this.restroomId = restroomId;
  }

  public EndOfUsingRestroomResponse toResponse() {
    return new EndOfUsingRestroomResponse(userId, restroomId);
  }
}
