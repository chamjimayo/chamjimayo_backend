package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SearchRequestDto {
	@Schema(description = "검색어")
	private final String searchWord;
	@Schema(description = "사용자 식별 아이디")
	private final Long userId;

	private SearchRequestDto(String searchWord, Long userId) {
		this.searchWord = searchWord;
		this.userId = userId;
	}

	public static SearchRequestDto create(String searchWord, Long userId) {
		return new SearchRequestDto(searchWord, userId);
	}
}
