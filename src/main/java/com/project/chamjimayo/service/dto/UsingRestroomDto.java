package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.RestroomDetailResponse;
import com.project.chamjimayo.controller.dto.response.UsingRestroomResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsingRestroomDto {
  long userId;
  long restroomId;

  public static UsingRestroomDto create(long userId,long restroomId){
    return new UsingRestroomDto(userId, restroomId);
  }
  public UsingRestroomResponse toResponse(){
    return new UsingRestroomResponse(userId,restroomId);
  }
}
