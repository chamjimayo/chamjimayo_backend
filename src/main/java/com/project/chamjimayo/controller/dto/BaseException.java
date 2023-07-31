package com.project.chamjimayo.controller.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BaseException extends Exception {

  private ErrorStatus status; //BaseResoinseStatus 객체에 매핑
}
