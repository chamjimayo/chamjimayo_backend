package com.project.chamjimayo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class BaseException extends Exception {
    private ErrorCode status; //BaseResoinseStatus 객체에 매핑
}
