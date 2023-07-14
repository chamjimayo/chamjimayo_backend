package com.project.chamjimayo.controller.dto;

import lombok.Getter;

@Getter
public class SearchRequestDto {
	private final String searchWord;
	private final Long userId;

	private SearchRequestDto(String searchWord, Long userId) {
		this.searchWord = searchWord;
		this.userId = userId;
	}

	public static SearchRequestDto create(String searchWord, Long userId) {
		return new SearchRequestDto(searchWord, userId);
	}
}
