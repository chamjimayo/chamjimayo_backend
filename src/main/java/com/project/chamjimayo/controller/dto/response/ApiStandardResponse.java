package com.project.chamjimayo.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ApiStandardResponse<T> {

  @Schema(description = "response code")
  private final String code;

  @Schema(description = "response message")
  private final String msg;

  @Schema(description = "response data")
  private final T data;

  private ApiStandardResponse(String code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> ApiStandardResponse<T> success(T data) {
    return new ApiStandardResponse<>("00", "success", data);
  }

  public static <T extends ErrorResponse> ApiStandardResponse<T> fail(T data) {
    return new ApiStandardResponse<>(data.getStatus().toString(), data.getMsg(), null);
  }
}