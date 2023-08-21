package com.project.chamjimayo.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsingRestroomResponse {

  long userId;

  long restroomId;

  Integer price;
}
