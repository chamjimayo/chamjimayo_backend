package com.project.chamjimayo.dto;

public class SearchRequestDto {

	private String searchWord;
	private Integer userId;

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
