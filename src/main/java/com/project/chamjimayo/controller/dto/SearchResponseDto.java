package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchResponseDto {

	@Schema(type = "string", example = "스타벅스")
	private final String searchWord;
	@Schema(type = "string", example = "서울특별시 용산구 동자로 123")
	private final String roadAddress;
	@Schema(type = "string", example = "서울특별시 용산구 동자동 12 345")
	private final String lotNumberAddress;
	@Schema(type = "string", example = "스타벅스 서울역점")
	private final String name;
	@Schema(type = "double", example = "12.3456")
	private final Double latitude;
	@Schema(type = "double", example = "12.3456")
	private final Double longitude;

	private SearchResponseDto(String searchWord, String roadAddress, String lotNumberAddress,
		String name, Double latitude, Double longitude) {
		this.searchWord = searchWord;
		this.roadAddress = roadAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static SearchResponseDto create(String searchWord, String roadAddress,
		String lotNumberAddress, String name, Double latitude, Double longitude) {
		return new SearchResponseDto(searchWord, roadAddress, lotNumberAddress, name,
			latitude, longitude);
	}
}

