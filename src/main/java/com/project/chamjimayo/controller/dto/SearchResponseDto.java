package com.project.chamjimayo.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchResponseDto {

	private final String roadAddress;
	private final String lotNumberAddress;
	private final String name;

	@Builder
	public SearchResponseDto(String roadAddress, String lotNumberAddress, String name) {
		this.roadAddress = roadAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.name = name;
	}
}

