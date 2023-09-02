package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.EndOfUsingRestroomResponse;
import lombok.Getter;

@Getter
public class EndOfUsingRestroomDto {

  private long userId;
  private long restroomId;
  private long usedRestroomId;

  public EndOfUsingRestroomDto(long userId) {
    this.userId = userId;
  }

  public static EndOfUsingRestroomDto create(long userId) {
    return new EndOfUsingRestroomDto(userId);
  }

  public void setRestroomId(long restroomId) {
    this.restroomId = restroomId;
  }

  public void setUsedRestroomId(long usedRestroomId){this.usedRestroomId = usedRestroomId;}

  public EndOfUsingRestroomResponse toResponse() {
    return new EndOfUsingRestroomResponse(userId, restroomId, usedRestroomId);
  }
}
