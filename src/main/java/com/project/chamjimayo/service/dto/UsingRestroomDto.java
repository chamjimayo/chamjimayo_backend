package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.RestroomDetailResponse;
import com.project.chamjimayo.controller.dto.response.UsingRestroomResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UsingRestroomDto {
  private long userId;
  private long restroomId;

  private Integer price;

  private UsingRestroomDto(long userId,long restroomId){
    this.userId = userId;
    this.restroomId = restroomId;
    price = 0;
  }

  public static UsingRestroomDto create(long userId,long restroomId){
    return new UsingRestroomDto(userId, restroomId);
  }
  public UsingRestroomResponse toResponse(){
    return new UsingRestroomResponse(userId,restroomId,price);
  }

  public void setPrice(Integer price) {
    this.price = price;
  }
}
