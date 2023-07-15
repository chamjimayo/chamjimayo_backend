package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchResponseDto {

	@Schema(description = "도로명 주소")
	private final String roadAddress;
	@Schema(description = "지번 주소")
	private final String lotNumberAddress;
	@Schema(description = "해당 위치의 상호명")
	private final String name;

	@Builder
	public SearchResponseDto(String roadAddress, String lotNumberAddress, String name) {
		this.roadAddress = roadAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.name = name;
	}
}

