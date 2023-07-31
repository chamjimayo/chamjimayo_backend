package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SearchResponseDto {

	@Schema(type = "string", example = "스타벅스")
	@NotBlank(message = "검색어를 입력해주세요.")
	private final String searchWord;

	@Schema(type = "string", example = "서울특별시 용산구 동자로 123")
	@NotBlank(message = "도로명 주소를 입력해주세요.")
	private final String roadAddress;

	@Schema(type = "string", example = "서울특별시 용산구 동자동 12 345")
	@NotBlank(message = "지번 주소를 입력해주세요.")
	private final String lotNumberAddress;

	@Schema(type = "string", example = "스타벅스 서울역점")
	@NotBlank(message = "가게 이름을 입력해주세요.")
	private final String name;

	@Schema(type = "double", example = "12.1234567")
	@NotNull(message = "위도를 입력해주세요.")
	@Min(value = -90, message = "위도는 -90 ~ 90으로 입력해주세요.")
	@Max(value = 90, message = "위도는 -90 ~ 90으로 입력해주세요.")
	private final Double latitude;

	@Schema(type = "double", example = "12.1234567")
	@NotNull(message = "경도를 입력해주세요.")
	@Min(value = -180, message = "경도는 -180 ~ 180으로 입력해주세요.")
	@Max(value = 180, message = "경도는 -180 ~ 180으로 입력해주세요.")
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

