package com.project.chamjimayo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DuplicateCheckDto {
  private final boolean isDuplicate;

  private DuplicateCheckDto(boolean isDuplicate) {
    this.isDuplicate = isDuplicate;
  }

  public static DuplicateCheckDto create(boolean isDuplicate) {
    return new DuplicateCheckDto(isDuplicate);
  }

  @Schema(name = "DuplicateCheckDto.Response")
  @Getter
  public static class Response {

    private final boolean isDuplicate;

    public Response(boolean isDuplicate) {
      this.isDuplicate = isDuplicate;
    }
  }

  public Response toResponse() {
    return new Response(isDuplicate);
  }
}
