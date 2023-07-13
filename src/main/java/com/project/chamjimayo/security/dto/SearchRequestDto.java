package com.project.chamjimayo.security.dto;

import lombok.Builder;

public class SearchRequestDto {

	private final String searchWord;
	private final Long userId;

	@Builder
	public SearchRequestDto(String searchWord, Long userId) {
		this.searchWord = searchWord;
		this.userId = userId;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public Long getUserId() {
		return userId;
	}

}
