package com.project.chamjimayo.security.dto;

import lombok.Builder;

public class SearchRequestDto {

	private final String searchWord;
	private final Integer userId;

	@Builder
	public SearchRequestDto(String searchWord, Integer userId) {
		this.searchWord = searchWord;
		this.userId = userId;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public Integer getUserId() {
		return userId;
	}

}
