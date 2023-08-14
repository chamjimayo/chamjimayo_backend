package com.project.chamjimayo.service.dto;

import com.project.chamjimayo.controller.dto.response.RestroomDetailResponse;
import com.project.chamjimayo.repository.domain.entity.Restroom;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RestroomDetailDto {
  private long restroomId;
  private Restroom restroom;

  public RestroomDetailDto(long restroomId){
    this.restroomId = restroomId;
  }

  public void setRestroom(Restroom restroom) {
    this.restroom = restroom;
  }

  public RestroomDetailResponse toResponse(){
    return new RestroomDetailResponse(restroom);
  }
}
