package com.project.chamjimayo.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndOfUsingRestroomResponse {

  long userId;

  long restroomId;

  long usedRestroomId;
}
