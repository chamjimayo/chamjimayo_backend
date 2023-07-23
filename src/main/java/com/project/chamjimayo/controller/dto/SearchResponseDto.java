package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchResponseDto {

	@Schema(type = "string", example = "검색 키워드")
	private final String searchWord;
	@Schema(type = "string", example = "도로명 주소")
	private final String roadAddress;
	@Schema(type = "string", example = "지번 주소")
	private final String lotNumberAddress;
	@Schema(type = "string", example = "해당 위치의 상호명")
	private final String name;
	@Schema(type = "double", example = "해당 위치의 위도")
	private final Double latitude;
	@Schema(type = "double", example = "해당 위치의 경도")
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

