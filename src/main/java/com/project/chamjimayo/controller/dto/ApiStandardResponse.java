package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApiStandardResponse<T> {
  @Schema(description = "response code")
  private final int code;

  @Schema(description = "response message")
  private final String msg;

  @Schema(description = "response data")
  private final T data;

  private ApiStandardResponse(int code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> ApiStandardResponse<T> success(T data) {
    return new ApiStandardResponse<>(200, "success", data);
  }

  public static <T> ApiStandardResponse<T> fail(T data, int code) {
    return new ApiStandardResponse<>(code, "fail", data);
  }
}